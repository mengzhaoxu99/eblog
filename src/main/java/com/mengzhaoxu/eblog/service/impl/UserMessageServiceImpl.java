package com.mengzhaoxu.eblog.service.impl;

import com.mengzhaoxu.eblog.entity.UserMessage;
import com.mengzhaoxu.eblog.mapper.UserMessageMapper;
import com.mengzhaoxu.eblog.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-06-01
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {

}
