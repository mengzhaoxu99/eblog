package com.mengzhaoxu.eblog.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.User;
import com.mengzhaoxu.eblog.service.UserService;
import com.mengzhaoxu.eblog.util.ValidationUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author yixin
 * @date 2020/6/8 11:39 上午
 * @description 登录注册
 */


@Controller
public class AuthController extends BaseController{

    private static final String VERIFYCODE="VERIFYCODE";

    @Autowired
    private UserService userService;



    @RequestMapping("capthca.jpg")
    public void verifyForImage(HttpServletResponse rep) throws IOException {

        //定义图形验证码的长和宽
        CircleCaptcha lineCaptcha = CaptchaUtil.createCircleCaptcha(150, 38,4,4);

        rep.setHeader("Cache-Control", "no-store, no-cache");
        rep.setContentType("image/jpeg");
        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write(rep.getOutputStream());

        req.getSession().setAttribute(VERIFYCODE,lineCaptcha);
        //输出code
        Console.log(lineCaptcha.getCode());
//        //验证图形验证码的有效性，返回boolean值
//        lineCaptcha.verify("1234");

    }

    @ResponseBody
    @PostMapping("register")
    public Result doRegister(User user,String repass,String vercode){
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(user);
        if (validResult.hasErrors()){
            return Result.error(validResult.getErrors());
        }

        if (!user.getPassword().equals(repass)){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        CircleCaptcha attribute = (CircleCaptcha) req.getSession().getAttribute(VERIFYCODE);
        if (attribute == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        if (!attribute.verify(vercode)){
            return Result.error(CodeMsg.VERIFY_ERROR);
        }
        return userService.register(user).action("/login");
//        return Result.success(CodeMsg.SUCCESS_REG);
    }


    @ResponseBody
    @PostMapping("login")
    public Result doLogin(String email,String password,String vercode){

        if(StrUtil.isBlank(email) || StrUtil.isBlank(password)){
            return Result.error(CodeMsg.LOGIN_ERROR);
        }
        if (StrUtil.isBlank(vercode)){
            return Result.error(CodeMsg.VERIFY_ERROR);
        }
        CircleCaptcha attribute = (CircleCaptcha) req.getSession().getAttribute(VERIFYCODE);
        if (!attribute.verify(vercode)){
            return Result.error(CodeMsg.VERIFY_ERROR);
        }

        UsernamePasswordToken token = new UsernamePasswordToken(email, SecureUtil.md5(password));
        try{
            SecurityUtils.getSubject().login(token);
        }catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                return Result.error("用户不存在");
            } else if (e instanceof LockedAccountException) {
                return Result.error("用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                return Result.error("密码错误");
            } else {
                return Result.error("用户认证失败");
            }
        }
        return Result.success(CodeMsg.SUCCESS_LOG).action("/");
    }



    @RequestMapping("user/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/index";
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
