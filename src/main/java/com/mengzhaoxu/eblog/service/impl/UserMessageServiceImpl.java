package com.mengzhaoxu.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.entity.UserMessage;
import com.mengzhaoxu.eblog.mapper.UserMessageMapper;
import com.mengzhaoxu.eblog.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengzhaoxu.eblog.vo.UserMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    UserMessageMapper userMessageMapper;

    @Override
    public IPage<UserMessageVo> paging(Page page, QueryWrapper<UserMessage> wrapper) {
        return userMessageMapper.selectMessages(page, wrapper);
    }

    @Override
    public void updateToReaded(List<Long> ids) {
        if(ids.isEmpty()) return;

        userMessageMapper.updateToReaded(new QueryWrapper<UserMessage>()
                .in("id", ids)
        );
    }
}
