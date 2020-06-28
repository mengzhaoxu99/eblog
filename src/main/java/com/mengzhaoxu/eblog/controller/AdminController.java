package com.mengzhaoxu.eblog.controller;

import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yixin
 * @date 2020/6/28 2:46 下午
 * @description 管理员操作
 */
@Controller
@RequestMapping("admin")
public class AdminController extends BaseController{


    @Autowired
    private PostService postService;


    @ResponseBody
    @PostMapping("jie-set")
    public Result jieSet(Long id, Integer rank, String field ){
        Post post = postService.getById(id);
        if (post == null){
            return Result.error("该帖子已被删除");
        }
        if("delete".equals(field)) {
            postService.removeById(id);
            return Result.success(CodeMsg.SUCCESS);

        } else if("status".equals(field)) {
            post.setRecommend(rank == 1);

        }  else if("stick".equals(field)) {
            post.setLevel(rank);
        }
        postService.updateById(post);
        return Result.success(CodeMsg.SUCCESS);
    }



}
