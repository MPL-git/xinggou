package com.xinggou.admin.bc.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.bc.vo.request.LoginRequest;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.admin.common.interceptor.Login;
import com.xinggou.bc.bean.CurrentStaff;
import com.xinggou.bc.service.BcModifyService;
import com.xinggou.common.constant.Const;
import com.xinggou.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Api(tags = "登录相关")
@ApiSupport(order = 10)
@Controller
public class LoginController extends BaseController {

    @Reference
    private BcModifyService bcModifyService;

    //    @Autowired
//    private Producer producer;
//    @ApiOperation("图片验证码")
//    @GetMapping("captcha.jpg")
//    public void captcha(HttpServletResponse response) throws IOException {
//        response.setHeader("Cache-Control", "no-store, no-cache");
//        response.setContentType("image/jpeg");
//
//        //生成文字验证码
//        String text = producer.createText();
//        //生成图片验证码
//        BufferedImage image = producer.createImage(text);
//        //保存到shiro session
//        httpServletRequest.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
//
//        ServletOutputStream out = response.getOutputStream();
//        ImageIO.write(image, "jpg", out);
//    }

    @Login(required = false)
    @ApiOperation(value = "登录接口")
    @ResponseBody
    @PostMapping(value = "login")
    public R<CurrentStaff> login(@RequestBody @Valid LoginRequest request) {
        CurrentStaff currentStaff = bcModifyService.login(request.getUserName(), request.getPassword(), getIpAddr());

        return R.ok(currentStaff);
    }

    @ApiOperation("退出登录")
    @ResponseBody
    @PostMapping(value = "logout")
    public R logout() {
        String token = httpServletRequest.getHeader(Const.AUTH_TOKEN);
        bcModifyService.logout(token);
        return R.ok();
    }

}
