package com.mengzhaoxu.eblog.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.entity.User;
import com.mengzhaoxu.eblog.oss.cloud.OSSFactory;
import com.mengzhaoxu.eblog.service.PostService;
import com.mengzhaoxu.eblog.service.UserService;
import com.mengzhaoxu.eblog.shiro.AccountProfile;
import com.qiniu.storage.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author yixin
 * @date 2020/6/12 10:13 上午
 * @description 用户相关
 */

@Controller
@RequestMapping("user")
public class UserController extends BaseController{


    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("home")
    public String home(){
        AccountProfile profile = getProfile();
        User user = userService.getById(profile.getId());

        List<Post> list = postService.list(new QueryWrapper<Post>()
                .eq("user_id", profile.getId())
                .orderByDesc("created")
        );
        req.setAttribute("user",user);
        req.setAttribute("posts",list);
        return "/user/home";
    }
    @GetMapping("set")
    public String set(){
        AccountProfile profile = getProfile();
        User user = userService.getById(profile.getId());
        req.setAttribute("user",user);
        return "/user/set";
    }


    @ResponseBody
    @PostMapping("set")
    public Result doSet(User user){

        if (StrUtil.isBlank(user.getUsername())){
            return Result.error(CodeMsg.USERNAME_ISNULL);
        }
        int count = userService.count(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .ne("id", getProfileId())
        );
        if (count>0){
            return Result.error(CodeMsg.USERNAME_EXIST);
        }
        User user1 = userService.getById(getProfileId());
        user1.setUsername(user.getUsername());
        user1.setGender(user.getGender());
        user1.setSign(user.getSign());
        userService.updateById(user1);


        AccountProfile profile = getProfile();
        profile.setUsername(user1.getUsername());
        profile.setSign(user1.getSign());
        return Result.success(CodeMsg.SUCCESS).action("/user/set#info");

    }
    @GetMapping("message")
    public String message(){
        return "/user/message";
    }


    @ResponseBody
    @RequestMapping("upload")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()){
            return Result.error(CodeMsg.USERAVATAR_ISNULL);
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.indexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(),suffix);

        if (url!=null){
            AccountProfile profile = getProfile();
            User user = userService.getById(profile.getId());
//            .substring(1)
            String key = user.getAvatar().substring(user.getAvatar().indexOf("/")).substring(1);
            user.setAvatar(url);
            boolean b = userService.updateById(user);
            if (b){
                profile.setAvatar(url);
                if (!StrUtil.isBlank(key) && !key.equals("test.png")){
                    try {
                        FileInfo stat = OSSFactory.build().stat(key);
                        OSSFactory.build().delete(key);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }
        return Result.success(CodeMsg.SUCCESS).action("/user/set#avatar");
    }

}
