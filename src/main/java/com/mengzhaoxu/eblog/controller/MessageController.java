package com.mengzhaoxu.eblog.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.UserMessage;
import com.mengzhaoxu.eblog.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author yixin
 * @date 2020/6/24 2:13 下午
 * @description 用户消息
 */
@Controller
@RequestMapping("message")
public class MessageController extends BaseController{

    @Autowired
    private UserMessageService userMessageService;


    @ResponseBody
    @PostMapping("remove")
    public Result remove(Long id,@RequestParam(defaultValue = "false") Boolean all){

        boolean remove = userMessageService.remove(new QueryWrapper<UserMessage>()
                .eq("to_user_id", getProfileId())
                .eq(!all, "id", id)
        );
        return remove ? Result.success(CodeMsg.SUCCESS) : Result.error(CodeMsg.SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping("nums")
    public Map msgNums() {

        int count = userMessageService.count(new QueryWrapper<UserMessage>()
                .eq("to_user_id", getProfileId())
                .eq("status", "0")
        );
        return MapUtil.builder("status", 0)
                .put("count", count).build();
    }




}
