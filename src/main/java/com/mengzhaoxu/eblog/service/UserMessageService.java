package com.mengzhaoxu.eblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengzhaoxu.eblog.vo.UserMessageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-06-01
 */
public interface UserMessageService extends IService<UserMessage> {

    IPage<UserMessageVo> paging(Page page, QueryWrapper<UserMessage> wrapper);

    void updateToReaded(List<Long> ids);
}
