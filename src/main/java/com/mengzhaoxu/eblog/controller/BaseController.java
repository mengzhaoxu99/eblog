package com.mengzhaoxu.eblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yixin
 * @date 2020/6/1 4:25 下午
 * @description
 */

public class BaseController {

    @Autowired
    HttpServletRequest req;

    public Page getPage(){
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 2);
        return new Page(pn,size);
    }


}
