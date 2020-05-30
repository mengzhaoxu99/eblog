package com.mengzhaoxu.eblog.service.impl;

import com.mengzhaoxu.eblog.entity.User;
import com.mengzhaoxu.eblog.mapper.UserMapper;
import com.mengzhaoxu.eblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
