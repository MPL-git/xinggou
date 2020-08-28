package com.xinggou.rc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.rc.dao.ScheduleJobDao;
import com.xinggou.rc.entity.ScheduleJob;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobBiz extends BaseBiz<ScheduleJobDao, ScheduleJob> {

    @Override
    protected QueryChainWrapper<ScheduleJob> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<ScheduleJob> wrapper = queryObject.fillWrapper(query());
        if(queryObject.getQuery("id") != null){
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }


}
