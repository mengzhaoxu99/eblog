package com.mengzhaoxu.eblog.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.entity.UserCollection;
import com.mengzhaoxu.eblog.service.PostService;
import com.mengzhaoxu.eblog.service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author yixin
 * @date 2020/6/24 3:32 下午
 * @description
 */
@Controller
@RequestMapping("collection")
public class CollectionController extends BaseController{

    @Autowired
    private UserCollectionService collectionService;
    @Autowired
    private PostService postService;



    @ResponseBody
    @PostMapping("find")
    public Result collectionFind(Long pid){

        int count = collectionService.count(new QueryWrapper<UserCollection>()
                .eq("user_id", getProfileId())
                .eq("post_id", pid)
        );
        return Result.success(MapUtil.of("collection",count>0));
    }


    @ResponseBody
    @PostMapping("add")
    public Result add(Long pid){
        Post post = postService.getById(pid);

        Assert.isTrue(post != null, "改帖子已被删除");
        int count = collectionService.count(new QueryWrapper<UserCollection>()
                .eq("user_id", getProfileId())
                .eq("post_id", pid)
        );
        if(count > 0) {
            return Result.error("你已经收藏");
        }

        UserCollection collection = new UserCollection();
        collection.setUserId(getProfileId());
        collection.setPostId(pid);
        collection.setCreated(new Date());
        collection.setModified(new Date());
        collection.setPostUserId(post.getUserId());
        collectionService.save(collection);
        return Result.success(CodeMsg.SUCCESS);
    }


    @ResponseBody
    @PostMapping("remove")
    public Result remove(Long pid){
        Post post = postService.getById(pid);
        Assert.isTrue(post != null, "改帖子已被删除");

        boolean remove = collectionService.remove(new QueryWrapper<UserCollection>()
                .eq("user_id", getProfileId())
                .eq("post_id", pid));
        return remove ? Result.success(CodeMsg.SUCCESS) : Result.error(CodeMsg.SERVER_ERROR);

    }

}
