package com.mengzhaoxu.eblog.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengzhaoxu.eblog.entity.Category;
import com.mengzhaoxu.eblog.service.CategoryService;
import com.mengzhaoxu.eblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * @author yixin
 * @date 2020/6/1 4:33 下午
 * @description 启动时加载导航栏
 */


@Component
public class ContextStartUp implements ApplicationRunner , ServletContextAware {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;



    ServletContext servletContext;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Category> categoryList = categoryService.list(new QueryWrapper<Category>().eq("status", 0));

        postService.initWeekRank();
        servletContext.setAttribute("categoryList",categoryList);


//        servletContext.setAttribute("categoryList",categoryList);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
