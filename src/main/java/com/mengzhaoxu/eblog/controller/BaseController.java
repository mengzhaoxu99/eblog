package com.mengzhaoxu.eblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yixin
 * @date 2020/6/1 4:25 下午
 * @description
 */

public class BaseController {

    @Autowired
    HttpServletRequest req;

}
