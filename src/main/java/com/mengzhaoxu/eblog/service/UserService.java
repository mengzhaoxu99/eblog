package com.mengzhaoxu.eblog.service;

import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengzhaoxu.eblog.shiro.AccountProfile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-05-28
 */
public interface UserService extends IService<User> {

    Result register(User user);

    AccountProfile login(String username, String password);
}
