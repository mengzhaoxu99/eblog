package com.mengzhaoxu.eblog.vo;

import com.mengzhaoxu.eblog.entity.Comment;
import lombok.Data;

/**
 * @author yixin
 * @date 2020/6/2 3:04 下午
 * @description 评论信息
 */
@Data
public class CommentVo extends Comment {

    private Long authorId;
    private String authorName;
    private String authorAvatar;


}
