package com.mengzhaoxu.eblog.service.impl;

import com.mengzhaoxu.eblog.entity.Category;
import com.mengzhaoxu.eblog.mapper.CategoryMapper;
import com.mengzhaoxu.eblog.service.CategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
