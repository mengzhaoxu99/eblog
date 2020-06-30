package com.mengzhaoxu.eblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mengzhaoxu.eblog.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yixin
 * @date 2020/6/30 2:44 下午
 * @description 搜素引擎接口
 */

@Controller
public class SeachController extends BaseController{

    @Autowired
    private SearchService searchService;

    @RequestMapping("search")
    public String search(String q) {

        IPage pageData = searchService.search(getPage(), q);

        req.setAttribute("q", q);
        req.setAttribute("pageData", pageData);
        return "search";
    }
}
