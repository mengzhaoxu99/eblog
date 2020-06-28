package com.mengzhaoxu.eblog.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.service.*;
import com.mengzhaoxu.eblog.util.ValidationUtil;
import com.mengzhaoxu.eblog.vo.CommentVo;
import com.mengzhaoxu.eblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author yixin
 * @date 2020/5/28 4:27 下午
 * @description
 */

@Controller
@RequestMapping("post")
public class PostController extends BaseController{

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserMessageService messageService;
    @Autowired
    private UserCollectionService collectionService;


    @GetMapping("{id:\\d*}")
    public String detail(@PathVariable("id") Long id){
        PostVo vo = postService.selectById(id);

        //1、分页信息 2、文章id 3、用户ID 4、排序
        IPage<CommentVo> results = commentService.paing(getPage(),vo.getId(),null,"created");

        req.setAttribute("currentCategoryId",vo.getCategoryId());
        req.setAttribute("post",vo);
        req.setAttribute("pageData",results);

        return "post/detail";
    }


    @GetMapping("edit")
    public String edit(){
        String id = req.getParameter("id");
        if(!StringUtils.isEmpty(id)) {
            Post post = postService.getById(id);
            Assert.isTrue(post != null, "改帖子已被删除");
            Assert.isTrue(post.getUserId().longValue() == getProfileId().longValue(), "没权限操作此文章");
            req.setAttribute("post", post);
        }
        req.setAttribute("categories", categoryService.list());
        return "/post/edit";
    }


    @ResponseBody
    @PostMapping("submit")
    public Result submit(Post post){
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(post);
        if (validResult.hasErrors()){
            return Result.error(validResult.getErrors());
        }

        if(post.getId() == null) {
            post.setUserId(getProfileId());

            post.setModified(new Date());
            post.setCreated(new Date());
            post.setCommentCount(0);
            post.setEditMode(null);
            post.setLevel(0);
            post.setRecommend(false);
            post.setViewCount(0);
            post.setVoteDown(0);
            post.setVoteUp(0);
            postService.save(post);
        } else {
            Post tempPost = postService.getById(post.getId());
            Assert.isTrue(tempPost.getUserId().longValue() == getProfileId().longValue(), "无权限编辑此文章！");
            tempPost.setTitle(post.getTitle());
            tempPost.setContent(post.getContent());
            tempPost.setCategoryId(post.getCategoryId());
            postService.updateById(tempPost);
        }
//        return Result.success(CodeMsg.SUCCESS);
        return Result.success(CodeMsg.SUCCESS).action("/post/"+post.getId());
    }


    @ResponseBody
    @PostMapping("delete")
    public Result delete(Long id){
        Post post = postService.getById(id);

        if (post == null){
            return Result.error("该帖子已被删除");
        }
        if (post.getUserId() != getProfileId()){
            return Result.error("无权限删除此文章!");
        }
        postService.removeById(id);
        // 删除相关消息、收藏等
        messageService.removeByMap(MapUtil.of("post_id", id));
        collectionService.removeByMap(MapUtil.of("post_id", id));
//        amqpTemplate.convertAndSend(RabbitConfig.es_exchage, RabbitConfig.es_bind_key,
//                new PostMqIndexMessage(post.getId(), PostMqIndexMessage.REMOVE));

        return Result.success(CodeMsg.SUCCESS);
    }


}
