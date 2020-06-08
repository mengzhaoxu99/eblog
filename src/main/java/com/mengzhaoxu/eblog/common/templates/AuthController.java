package com.mengzhaoxu.eblog.common.templates;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yixin
 * @date 2020/6/8 11:39 上午
 * @description 登录注册
 */


@Controller
public class AuthController {

    @RequestMapping("capthca.jpg")
    public void verifyForImage(HttpServletResponse rep) throws IOException {

        //定义图形验证码的长和宽
        CircleCaptcha lineCaptcha = CaptchaUtil.createCircleCaptcha(150, 38,4,4);

        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write(rep.getOutputStream());
//        //输出code
//        Console.log(lineCaptcha.getCode());
//        //验证图形验证码的有效性，返回boolean值
//        lineCaptcha.verify("1234");
//
//        //重新生成验证码
//        lineCaptcha.createCode();
//        lineCaptcha.write("d:/line.png");
//        //新的验证码
//        Console.log(lineCaptcha.getCode());
//        //验证图形验证码的有效性，返回boolean值
//        lineCaptcha.verify("1234");

    }


    @RequestMapping("login")
    public String login(){
        return "auth/login";
    }
    @RequestMapping("register")
    public String reg(){
        return "auth/reg";
    }

}
