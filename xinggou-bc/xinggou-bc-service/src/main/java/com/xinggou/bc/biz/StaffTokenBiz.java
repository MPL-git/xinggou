package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xinggou.bc.constant.BcRedisKeys;
import com.xinggou.bc.dao.StaffTokenDao;
import com.xinggou.bc.entity.StaffToken;
import com.xinggou.common.config.JwtOperator;
import com.xinggou.common.constant.Const;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.QueryObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class StaffTokenBiz extends BaseBiz<StaffTokenDao, StaffToken> {

    @Autowired
    private StaffBiz staffBiz;
    @Resource
    private JwtOperator jwtOperator;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public StaffToken save(long staffId, String token, String refreshToken, String ip) {
        StaffToken model = findByIp(staffId, ip);
        if(model == null){
            model = new StaffToken();
            model.setStaffId(staffId);
            model.setIp(ip);
        }

        model.setToken(token);
        model.setExpiretime(DateTime.now().plusSeconds(jwtOperator.getStaffTokenExpireTimeInSecond()).toDate());

        if(model.getId() == null || model.getId() == 0){
            save(model);
        }else{
            updateById(model);
        }

        // 保存refreshToken至redis，使用hash结构保存使用中的token以及用户标识
        String refreshTokenKey = BcRedisKeys.getRefreshTokenKey(refreshToken);
        stringRedisTemplate.opsForHash().put(refreshTokenKey, Const.AUTH_TOKEN, token);
        stringRedisTemplate.opsForHash().put(refreshTokenKey, Const.AUTH_STAFF_ID, ""+staffId);
        //refreshToken设置过期时间
        stringRedisTemplate.expire(refreshTokenKey, jwtOperator.getStaffRefreshTokenExpireTimeInSecond(), TimeUnit.SECONDS);

        // 保存用户权限到redis
        Set<String> perms = staffBiz.listStaffPerm(staffId);
        stringRedisTemplate.delete(BcRedisKeys.getStaffPermsKey(staffId));
        stringRedisTemplate.opsForSet().add(BcRedisKeys.getStaffPermsKey(staffId), perms.toArray(new String[0]));

        return model;
    }

    public void disabledToken(long staffId, String token){
        StaffToken staffToken = findByToken(staffId, token);
        if(staffToken == null) {
            return;
        }

        // 删除token
        removeById(staffToken.getId());

        // 将旧token添加到黑名单
        stringRedisTemplate.opsForValue().set(BcRedisKeys.getBlackTokenKey(token), "", jwtOperator.getStaffTokenExpireTimeInSecond(), TimeUnit.SECONDS);
    }

    public String refreshToken(String refreshToken){
        String refreshTokenKey = BcRedisKeys.getRefreshTokenKey(refreshToken);
        if(!stringRedisTemplate.opsForHash().hasKey(refreshTokenKey, Const.AUTH_STAFF_ID)){
            throw new BizException("refreshToken过期");
        }
        Long staffId = (Long)stringRedisTemplate.opsForHash().get(refreshTokenKey, Const.AUTH_STAFF_ID);
        String oldToken = (String)stringRedisTemplate.opsForHash().get(refreshTokenKey, Const.AUTH_TOKEN);

        // 将旧token添加到黑名单
        stringRedisTemplate.opsForValue().set(BcRedisKeys.getBlackTokenKey(oldToken), "", jwtOperator.getStaffTokenExpireTimeInSecond(), TimeUnit.SECONDS);

        // 生成新token
        String newToken = jwtOperator.generateStaffToken(staffId);
        stringRedisTemplate.opsForHash().put(refreshTokenKey, Const.AUTH_TOKEN, newToken);

        StaffToken model = findByToken(staffId, oldToken);
        model.setToken(newToken);
        model.setExpiretime(DateTime.now().plusSeconds(jwtOperator.getStaffTokenExpireTimeInSecond()).toDate());
        updateById(model);
        return newToken;
    }


    public StaffToken findByToken(long staffId, String token) {
        QueryObject queryObject = new QueryObject();
        queryObject.addQuery("staffId", staffId);
        queryObject.addQuery("token", token);
        return find(queryObject);
    }

    public StaffToken findByIp(long staffId, String ip) {
        QueryObject queryObject = new QueryObject();
        queryObject.addQuery("staffId", staffId);
        queryObject.addQuery("ip", ip);
        return find(queryObject);
    }


    @Override
    protected QueryChainWrapper<StaffToken> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<StaffToken> wrapper = queryObject.fillWrapper(query());
        if(queryObject.getQuery("staffId") != null){
            wrapper.eq("staff_id", queryObject.getQuery("staffId"));
        }
        if(queryObject.getQuery("token") != null){
            wrapper.eq("token", queryObject.getQuery("token"));
        }
        if(queryObject.getQuery("ip") != null){
            wrapper.eq("ip", queryObject.getQuery("ip"));
        }
        return wrapper;
    }
}
