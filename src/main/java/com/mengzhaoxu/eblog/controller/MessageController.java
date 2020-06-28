package com.mengzhaoxu.eblog.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.Comment;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.entity.User;
import com.mengzhaoxu.eblog.entity.UserMessage;
import com.mengzhaoxu.eblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
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
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;
    @Autowired
    private WbService wbService;

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


    @ResponseBody
    @PostMapping("reply")
    public Result reply(Long jid,String content){
        Assert.notNull(jid, "找不到对应的文章");
        Assert.hasLength(content, "评论内容不能为空");

        Post post = postService.getById(jid);
        Assert.isTrue(post != null, "该文章已被删除");

        Comment comment = new Comment();
        comment.setPostId(jid);
        comment.setContent(content);
        comment.setUserId(getProfileId());
        comment.setCreated(new Date());
        comment.setModified(new Date());
        comment.setLevel(0);
        comment.setVoteDown(0);
        comment.setVoteUp(0);
        commentService.save(comment);

        // 评论数量加一
        post.setCommentCount(post.getCommentCount() + 1);
        postService.updateById(post);

        // 本周热议数量加一
        postService.incrCommentCountAndUnionForWeekRank(post.getId(), true);
        // 通知作者，有人评论了你的文章
        // 作者自己评论自己文章，不需要通知
        if(comment.getUserId() != post.getUserId()) {
            UserMessage message = new UserMessage();
            message.setPostId(jid);
            message.setCommentId(comment.getId());
            message.setFromUserId(getProfileId());
            message.setToUserId(post.getUserId());
            message.setType(1);
            message.setContent(content);
            message.setCreated(new Date());
            message.setStatus(0);
            userMessageService.save(message);


            // 即时通知作者（websocket）
            wbService.sendMessCountToUser(message.getToUserId());
        }
        if(content.startsWith("@")) {
            String username = content.substring(1, content.indexOf(" "));
            System.out.println(username);

            User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
            if(user != null) {
                UserMessage message = new UserMessage();
                message.setPostId(jid);
                message.setCommentId(comment.getId());
                message.setFromUserId(getProfileId());
                message.setToUserId(user.getId());
                message.setType(2);
                message.setContent(content);
                message.setCreated(new Date());
                message.setStatus(0);
                userMessageService.save(message);

                // 即时通知被@的用户
            }
        }



        return Result.success(CodeMsg.SUCCESS).action("/post/"+post.getId());

    }


    @ResponseBody
    @Transactional
    @PostMapping("jieda-delete")
    public Result jiedaDelete(Long id) {

        Assert.notNull(id, "评论id不能为空！");

        Comment comment = commentService.getById(id);

        Assert.notNull(comment, "找不到对应评论！");

        if(comment.getUserId().longValue() != getProfileId().longValue()) {
            return Result.error("不是你发表的评论！");
        }
        commentService.removeById(id);

        // 评论数量减一
        Post post = postService.getById(comment.getPostId());
        post.setCommentCount(post.getCommentCount() - 1);
        postService.saveOrUpdate(post);

        //评论数量减一
        postService.incrCommentCountAndUnionForWeekRank(comment.getPostId(), false);

        return Result.success(null);
    }




}
