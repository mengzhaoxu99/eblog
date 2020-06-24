package com.mengzhaoxu.eblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.service.CommentService;
import com.mengzhaoxu.eblog.service.PostService;
import com.mengzhaoxu.eblog.vo.CommentVo;
import com.mengzhaoxu.eblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yixin
 * @date 2020/5/28 4:27 下午
 * @description
 */

@Controller
public class PostController extends BaseController{

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable("id") Long id){
        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        req.setAttribute("currentCategoryId",id);
        req.setAttribute("pn",pn);
        return "post/category";
    }

    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable("id") Long id){
        PostVo vo = postService.selectById(id);

        //1、分页信息 2、文章id 3、用户ID 4、排序
        IPage<CommentVo> results = commentService.paing(getPage(),vo.getId(),null,"created");

        req.setAttribute("currentCategoryId",vo.getCategoryId());
        req.setAttribute("post",vo);
        req.setAttribute("pageData",results);

        return "post/detail";
    }




}
