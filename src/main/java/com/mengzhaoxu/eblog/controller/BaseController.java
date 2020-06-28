package com.mengzhaoxu.eblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
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

    protected AccountProfile getProfile(){
       return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
    protected Long getProfileId() {
        if (getProfile() == null){
          return -1L;
        }
        return getProfile().getId();
    }


}
