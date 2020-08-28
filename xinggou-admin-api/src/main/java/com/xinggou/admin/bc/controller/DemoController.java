package com.xinggou.admin.bc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xinggou.bc.service.DemoService;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.utils.R;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试
 */
@ApiIgnore
@RestController
public class DemoController {

    @Reference
    private DemoService demoService;

    @ApiOperation("测试demo")
    @RequestMapping("test")
    public R list(QueryObject queryObject, String name) {
        Map<String, Object> data = new HashMap<>();
        data.put("hello", "world");
        data.put("test", "司马无情");
        data.put("test2", "欧阳无敌3");
        return R.ok(data);
    }

    @ApiOperation("hello Demo")
    @GetMapping("/hello")
    public R hello(String msg) {
        String res = demoService.sayHello(msg);

        return R.ok(res);
    }

    /**
     * 信息
     */
    @ApiOperation("hello Demo")
    @GetMapping("/test1")
    public R test1(String msg) {
        List<JSONObject> list = new ArrayList<>();
        JSONObject notice1 = new JSONObject();
        notice1.put("id", 10);
        notice1.put("title", "站内信");

        List<JSONObject> systemMsg = new ArrayList<>();
        JSONObject msg1 = new JSONObject();
        msg1.put("id", 101);
        msg1.put("avatar", "statics/libs/pear/admin/images/success.png");
        msg1.put("title", "武林令牌");
        msg1.put("context", "请速到武当一趟");
        msg1.put("form", "三丰1");
        msg1.put("time", "2019-02-15");
        systemMsg.add(msg1);
        systemMsg.add(msg1);
        systemMsg.add(msg1);

        notice1.put("children", systemMsg);
        list.add(notice1);

        JSONObject notice2 = new JSONObject();
        notice2.put("id", 20);
        notice2.put("title", "消息");

        List<JSONObject> messagelist = new ArrayList<>();
        JSONObject msg2 = new JSONObject();
        msg2.put("id", 101);
        msg2.put("avatar", "statics/libs/pear/admin/images/success.png");
        msg2.put("title", "华山武3林令牌");
        msg2.put("context", "请速到5华山一趟");
        msg2.put("form", "三丰");
        msg2.put("time", "2019-02-15");
        messagelist.add(msg2);
        messagelist.add(msg2);
        messagelist.add(msg2);

        notice2.put("children", messagelist);
        list.add(notice2);

        return R.ok(list);
    }


}
