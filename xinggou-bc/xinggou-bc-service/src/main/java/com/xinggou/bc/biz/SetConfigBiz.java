package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xinggou.bc.bean.params.PaginateConfigParams;
import com.xinggou.bc.bean.params.SaveConfigParams;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.dao.SetConfigDao;
import com.xinggou.bc.entity.SetConfig;
import com.xinggou.bc.enums.ConfigStatusEnum;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.utils.StrKit;
import com.xinggou.common.vo.PageWrap;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class SetConfigBiz extends BaseBiz<SetConfigDao, SetConfig> {

    public SetConfig saveInfo(SaveConfigParams params) {
        SetConfig config;
        if (params.getId() == null || params.getId() == 0L) {
            config = new SetConfig();
        } else {
            config = getById(params.getId());
        }

        config.setParamKey(params.getParamKey());
        config.setParamValue(params.getParamValue());
        config.setName(params.getName());
        config.setRemarks(params.getRemarks());
        config.setSeqNo(params.getSeqNo());
        if (config.getId() == null || config.getId() == 0) {
            config.setCreateDate(new Date());
            config.setDelFlag(StateConst.FALSE);
            config.setStatus(ConfigStatusEnum.SHOW);
            this.save(config);
        } else {
            config.setUpdateDate(new Date());
            this.updateById(config);
        }
        return config;
    }

    public void updateValueByKey(String key, String value) {
        SetConfig model = findByKey(key);
        if (model != null) {
            model.setParamValue(value);
            model.setUpdateDate(new Date());
            updateById(model);
        }
    }

    public void delete(List<Long> idList) {
        this.removeByIds(idList);
    }


    @Override
    protected QueryChainWrapper<SetConfig> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<SetConfig> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("id") != null) {
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }


    public SetConfig findByKey(String key) {
        return lambdaQuery().eq(SetConfig::getParamKey, key).one();
    }

    public String getValue(String key) {
        SetConfig model = findByKey(key);
        return model == null ? null : model.getParamValue();
    }

    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StrKit.notBlank(value)) {
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BizException("获取参数失败");
        }
    }

    public PageWrap<SetConfig> paginateConfig(PaginateConfigParams params) {
        IPage<SetConfig> iPage = lambdaQuery()
                .like(StrKit.notBlank(params.getName()), SetConfig::getName, params.getName())
                .eq(params.getStatus() != null, SetConfig::getStatus, params.getStatus())
                .orderByDesc(SetConfig::getName)
                .orderByAsc(SetConfig::getSeqNo)
                .page(new Page<>(params.getCurrentPage(), params.getPageSize()));
        return PageWrap.of(iPage);
    }

    public void saveConfigByKey(SaveConfigParams params) {
        SetConfig config = getByKey(params.getParamKey());
        if (config == null) {
            config = BcBizConverter.INSTANCE.toConfig(params);
            config.setSeqNo(0);
            config.setCreateDate(new Date());
            config.setDelFlag(StateConst.FALSE);
            config.setStatus(ConfigStatusEnum.SHOW);
            this.save(config);
        } else {
            config.setName(params.getName());
            config.setParamValue(params.getParamValue());
            config.setUpdateDate(new Date());
            this.updateById(config);
        }
    }

    private SetConfig getByKey(String paramKey) {
        List<SetConfig> list = lambdaQuery()
                .eq(SetConfig::getParamKey, paramKey)
                .eq(SetConfig::getDelFlag, StateConst.FALSE)
                .list();
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}
