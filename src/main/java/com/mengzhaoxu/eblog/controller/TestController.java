package com.mengzhaoxu.eblog.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author yixin
 * @date 2020/6/3 3:47 下午
 * @description
 */
public class TestController {


    public static void main(String[] args) {


//        DateTime dateTime = DateUtil.lastWeek();
        long l = DateUtil.betweenDay(new Date(), DateUtil.parseDate("2020-06-01"), false);
        System.out.println(l);
    }
}
