package com.xinggou.rc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.rc.dao.ScheduleJobLogDao;
import com.xinggou.rc.entity.ScheduleJobLog;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobLogBiz extends BaseBiz<ScheduleJobLogDao, ScheduleJobLog> {

    @Override
    protected QueryChainWrapper<ScheduleJobLog> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<ScheduleJobLog> wrapper = queryObject.fillWrapper(query());
        if(queryObject.getQuery("id") != null){
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }


}
