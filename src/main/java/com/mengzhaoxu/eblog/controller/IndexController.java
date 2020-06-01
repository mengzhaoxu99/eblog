package com.mengzhaoxu.eblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yixin
 * @date 2020/5/28 3:51 下午
 * @description
 */

@Controller
public class IndexController extends BaseController{


    @RequestMapping({"","/","index"})
    public String index(){
        req.setAttribute("currentCategoryId",0);
        return "index";
    }
}
