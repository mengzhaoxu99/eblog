package com.mengzhaoxu.eblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-06-01
 */
public interface PostService extends IService<Post> {
    //1、分页信息，2、分类，3、用户 4、置顶，5、精选，6、排序
    IPage paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);
}
