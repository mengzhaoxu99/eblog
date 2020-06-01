package com.mengzhaoxu.eblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yixin
 * @date 2020/5/28 3:51 下午
 * @description
 */

@Controller
public class IndexController extends BaseController{



    @Autowired
    private PostService postService;



    @RequestMapping({"","/","index"})
    public String index(){

        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 2);

        Page page = new Page(pn,size);
        //1、分页信息，2、分类，3、用户 4、置顶，5、精选，6、排序
        IPage results = postService.paging(page,null,null,null,null,"created");

        req.setAttribute("pageData",results);
        req.setAttribute("currentCategoryId",0);
        return "index";
    }
}
