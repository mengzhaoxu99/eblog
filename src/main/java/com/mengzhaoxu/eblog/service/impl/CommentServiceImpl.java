package com.mengzhaoxu.eblog.service.impl;

import com.mengzhaoxu.eblog.entity.Comment;
import com.mengzhaoxu.eblog.mapper.CommentMapper;
import com.mengzhaoxu.eblog.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
