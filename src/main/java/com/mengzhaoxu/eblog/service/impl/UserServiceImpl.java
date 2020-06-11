package com.mengzhaoxu.eblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.User;
import com.mengzhaoxu.eblog.mapper.UserMapper;
import com.mengzhaoxu.eblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengzhaoxu.eblog.shiro.AccountProfile;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-05-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



    @Override
    public Result register(User user) {
        int username = this.count(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (username>0){
            return Result.error(CodeMsg.USERNAME_EXIST);
        }
        int email = this.count(new QueryWrapper<User>().eq("email", user.getEmail()));
        if (email>0){
            return Result.error(CodeMsg.EMAIL_EXIST);
        }
        user.setUsername(user.getUsername());
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setAvatar("/res/images/avatar/default.png");
        user.setCreated(new Date());
        user.setPoint(0);
        user.setVipLevel(0);
        user.setCommentCount(0);
        user.setPostCount(0);
        user.setGender("0");
        boolean save = this.save(user);
        if (save){
            return Result.success(CodeMsg.SUCCESS_REG);
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @Override
    public AccountProfile login(String email, String password) {

        User user = this.getOne(new QueryWrapper<User>().eq("email", email));
        if (user==null){
            throw new UnknownAccountException();
        }
        if(!user.getPassword().equals(password)){
            throw new IncorrectCredentialsException();
        }
        user.setLasted(new Date());
        this.updateById(user);
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
//        profile.setUsername(user.getUsername());
        return profile;
    }
}
