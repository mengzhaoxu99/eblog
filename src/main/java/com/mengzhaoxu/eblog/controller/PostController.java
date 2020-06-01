package com.mengzhaoxu.eblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author yixin
 * @date 2020/5/28 4:27 下午
 * @description
 */

@Controller
public class PostController extends BaseController{



    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable("id") Long id){
        req.setAttribute("currentCategoryId",id);
        return "post/category";
    }

    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable("id") Long id){
        return "post/detail";
    }

}
