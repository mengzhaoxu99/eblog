package com.mengzhaoxu.eblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yixin
 * @date 2020/6/28 1:58 下午
 * @description 分类模块
 */
@Controller
@RequestMapping("category")
public class CategoryController extends BaseController{

    @GetMapping("{id:\\d*}")
    public String category(@PathVariable("id") Long id){
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        req.setAttribute("currentCategoryId",id);
        req.setAttribute("pn",pn);
        return "post/category";
    }



}
