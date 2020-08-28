package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xinggou.bc.bean.CurrentStaff;
import com.xinggou.bc.bean.dto.StaffDTO;
import com.xinggou.bc.bean.params.staff.CreateStaffParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffParams;
import com.xinggou.bc.bean.params.staff.UpdateStaffParams;
import com.xinggou.bc.constant.BcConst;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.dao.StaffDao;
import com.xinggou.bc.entity.Staff;
import com.xinggou.bc.entity.StaffLogLogin;
import com.xinggou.bc.enums.MenuTypeEnum;
import com.xinggou.bc.enums.StaffStatusEnum;
import com.xinggou.common.config.JwtOperator;
import com.xinggou.common.constant.Const;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.exception.Assert;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.HashKit;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.utils.RandomUtil;
import com.xinggou.common.vo.PageWrap;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StaffBiz extends BaseBiz<StaffDao, Staff> {

    @Autowired
    private SetMenuBiz menuBiz;
    @Autowired
    private DeptBiz deptBiz;
    @Autowired
    private StaffTokenBiz staffTokenBiz;
    @Autowired
    private StaffLogLoginBiz staffLogLoginBiz;
    @Resource
    private JwtOperator jwtOperator;


    public CurrentStaff login(Staff staff, String ip) {
        /*staff.setLastLoginIp(ip);
        staff.setLastLoginTime(new Date());
        staff.setLoginCount(staff.getLoginCount() + 1);
        update(staff);*/

        // 生成token
        String token = jwtOperator.generateStaffToken(staff.getId());
        String refreshToken = RandomUtil.randomUUID();
        staffTokenBiz.save(staff.getId(), token, refreshToken, ip);

        // 登录日志
        StaffLogLogin staffLogLogin = new StaffLogLogin();
        staffLogLogin.setStaffId(staff.getId());
        staffLogLogin.setIp(ip);
        staffLogLoginBiz.save(staffLogLogin);

        return transCurrentStaff(staff, token, refreshToken);
    }

    public void logout(String token) {
        long staffId = jwtOperator.getStaffId(token);
        if (staffId == 0) {
            throw new BizException("无效的token");
        }

        staffTokenBiz.disabledToken(staffId, token);

        // 登出日志
        //logLoginBiz.logout(account.getUserName(), accountToken.getIp());
    }

    @Transactional
    public Staff createStaff(CreateStaffParams params) {
        Staff staff = BcBizConverter.INSTANCE.toStaff(params);

        staff.setGender(params.getGender());
        staff.setSalt(genPasswordSalt());
        staff.setPassword(encodePassword(BcConst.DEFAULT_STAFF_PASSWORD, staff.getSalt()));
        staff.setStatus(StaffStatusEnum.NORMAL);
        this.save(staff);
        return staff;
    }

    @Transactional
    public Staff updateStaff(UpdateStaffParams params) {
        Staff staff = this.getById(params.getId());
        Assert.notNull(staff, "ID为[" + params.getId() + "]的用户不存在");

        BcBizConverter.INSTANCE.updateStaff(params, staff);
        this.updateById(staff);
        return staff;
    }

    @Transactional
    public void updatePassword(Long id, String password) {
        Staff staff = getById(id);
        Assert.notNull(staff, "ID为[" + id + "]的用户不存在");

        staff.setSalt(genPasswordSalt());
        staff.setPassword(encodePassword(password, staff.getSalt()));
        staff.setUpdateDate(new Date());
        this.updateById(staff);
    }

    public void disabledById(Long staffId) {
        Staff model = getById(staffId);
        model.setStatus(StaffStatusEnum.PROHIBIT);
        updateById(model);
    }

    public void activeById(Long id) {
        Staff model = getById(id);
        model.setStatus(StaffStatusEnum.NORMAL);
        updateById(model);
    }


    public Staff findByUserName(String username) {
        return lambdaQuery().eq(Staff::getUserName, username).one();
    }

    public Staff findByMobile(String mobile) {
        return lambdaQuery().eq(Staff::getMobile, mobile).one();
    }

    public Staff findByPassword(String userName, String password) {
        Staff staff = findByUserName(userName);
        if (staff == null) {
            return null;
        }

        if (!staff.getPassword().equals(encodePassword(password, staff.getSalt()))) {
            return null;
        }
        return staff;
    }

    public Set<String> listStaffPerm(Long staffId) {
        List<String> list;
        //系统管理员，拥有最高权限
        if (staffId == BcConst.SUPER_ADMIN) {
            QueryObject queryObject = new QueryObject();
            queryObject.addQuery("type", MenuTypeEnum.PERM.getValue());
            list = menuBiz.listPerms();
        } else {
            list = baseMapper.listStaffPerm(staffId);
        }

        //用户权限列表
        return list.stream()
                .map(perms -> Arrays.asList(perms.trim().split(Const.COMMA)))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 辅助方法
    // -----------------------------------------------------------------------------------------------------------------

    @Override
    protected QueryChainWrapper<Staff> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<Staff> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("deptId") != null) {
            wrapper.eq("dept_id", queryObject.getQuery("deptId"));
        }
        return wrapper;
    }

    private String genPasswordSalt() {
        //sha256加密
        return RandomStringUtils.randomAlphanumeric(20);
    }

    private String encodePassword(String password, String salt) {
        String salted = password + "{" + salt + "}";
        String digest = HashKit.sha1(salted);

        // 对密码进行5000次sha1加密
        for (int index = 1; index < 5000; index++) {
            digest = HashKit.sha1(digest + salted);
        }

        return HashKit.sha256(digest);
    }

    private CurrentStaff transCurrentStaff(Staff staff, String token, String refreshToken) {
        CurrentStaff currentStaff = new CurrentStaff();
        currentStaff.setId(staff.getId());
        currentStaff.setUserName(staff.getUserName());
        currentStaff.setMobile(staff.getMobile());
        currentStaff.setNickName(staff.getNickName());
        currentStaff.setAvatar(staff.getAvatar());
        currentStaff.setGender(staff.getGender());
        currentStaff.setToken(token);
        currentStaff.setRefreshToken(refreshToken);
        return currentStaff;
    }


    public static void main(String[] args) {
        String salt = "3WCJ6psAzEo6G2h16tnz";

        String salted = "admin" + "{" + salt + "}";
        String digest = HashKit.sha1(salted);

        // 对密码进行5000次sha1加密
        for (int index = 1; index < 5000; index++) {
            digest = HashKit.sha1(digest + salted);
        }

        System.out.println("pwd:" + HashKit.sha256(digest));
    }

    public PageWrap<StaffDTO> findStaff(PaginateStaffParams params) {
        int totalCount = baseMapper.countStaff(params);
        List<StaffDTO> list = Collections.emptyList();
        if (totalCount > 0) {
            list = baseMapper.findStaff(params);
        }
        return PageWrap.of(list, totalCount, params.getPageSize(), params.getCurrentPage());
    }

    public Map<Long, String> buildIdNameMap(List<Long> staffIds) {
        if (CollectionUtils.isEmpty(staffIds)) {
            return Collections.emptyMap();
        }
        List<Staff> list = lambdaQuery()
                .select(Staff::getId, Staff::getRealName)
                .in(Staff::getId, staffIds)
                .eq(Staff::getDelFlag, StateConst.FALSE)
                .list();
        return list.stream().collect(Collectors.toMap(Staff::getId, Staff::getRealName));
    }
}
