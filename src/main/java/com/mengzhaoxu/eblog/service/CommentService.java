package com.mengzhaoxu.eblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengzhaoxu.eblog.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-06-01
 */
public interface CommentService extends IService<Comment> {

    IPage<CommentVo> paing(Page page, Long postId, Long userId, String order);
}
