package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinggou.bc.bean.params.PaginateDictParams;
import com.xinggou.bc.bean.params.SaveDictParams;
import com.xinggou.bc.dao.SetDictDao;
import com.xinggou.bc.entity.SetDict;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.utils.StrKit;
import com.xinggou.common.vo.PageWrap;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SetDictBiz extends BaseBiz<SetDictDao, SetDict> {

    public SetDict saveDict(SaveDictParams params) {
        SetDict dict;
        if (params.getId() == null || params.getId() == 0L) {
            dict = new SetDict();
        } else {
            dict = this.getById(params.getId());
        }
        dict.setName(params.getName());
        dict.setType(params.getType());
        dict.setCode(params.getCode());
        dict.setValue(params.getValue());
        dict.setSeqNo(params.getSeqNo());
        if (dict.getId() == null || dict.getId() == 0) {
            dict.setCreateDate(new Date());
            dict.setDelFlag(StateConst.FALSE);
            save(dict);
        } else {
            dict.setUpdateDate(new Date());
            this.updateById(dict);
        }
        return dict;
    }

    @Override
    protected QueryChainWrapper<SetDict> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<SetDict> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("id") != null) {
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }

    public PageWrap<SetDict> findDict(PaginateDictParams params) {
        IPage<SetDict> iPage = lambdaQuery()
                .like(StrKit.notBlank(params.getName()), SetDict::getName, params.getName())
                .like(StrKit.notBlank(params.getType()), SetDict::getType, params.getType())
                .eq(SetDict::getDelFlag, StateConst.FALSE)
                .orderByAsc(SetDict::getSeqNo)
                .page(new Page<>(params.getCurrentPage(), params.getPageSize()));
        return PageWrap.of(iPage);
    }
}
