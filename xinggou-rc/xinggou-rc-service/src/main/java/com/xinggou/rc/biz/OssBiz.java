package com.xinggou.rc.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.vo.PageWrap;
import com.xinggou.rc.bean.params.PaginateOssFileParams;
import com.xinggou.rc.dao.OssDao;
import com.xinggou.rc.entity.Oss;
import org.springframework.stereotype.Service;

@Service
public class OssBiz extends BaseBiz<OssDao, Oss> {

    @Override
    protected QueryChainWrapper<Oss> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<Oss> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("id") != null) {
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }

    public PageWrap<Oss> findOssFile(PaginateOssFileParams params) {
        IPage<Oss> iPage = lambdaQuery()
                .eq(Oss::getDelFlag, StateConst.FALSE)
                .ge(params.getBeginDate() != null, Oss::getCreateDate, params.getBeginDate())
                .le(params.getEndDate() != null, Oss::getCreateDate, params.getEndDate())
                .orderByDesc(Oss::getId)
                .page(new Page<>(params.getCurrentPage(), params.getPageSize()));
        return PageWrap.of(iPage);
    }
}
