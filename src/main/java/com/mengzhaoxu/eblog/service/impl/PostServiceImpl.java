package com.mengzhaoxu.eblog.service.impl;

import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.mapper.PostMapper;
import com.mengzhaoxu.eblog.service.PostService;
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
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
