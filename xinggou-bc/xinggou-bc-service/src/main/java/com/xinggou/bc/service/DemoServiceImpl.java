package com.xinggou.bc.service;

import com.xinggou.bc.biz.DeptBiz;
import com.xinggou.rc.service.RcQueryService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DemoServiceImpl implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Autowired
    private DeptBiz deptBiz;
    @Reference
    private RcQueryService rcQueryService;

    @Override
    public String sayHello(String name) {
        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello my dear " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

}
