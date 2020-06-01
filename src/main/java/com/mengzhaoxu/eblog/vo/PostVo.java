package com.mengzhaoxu.eblog.vo;

import com.mengzhaoxu.eblog.entity.Post;
import lombok.Data;

/**
 * @author yixin
 * @date 2020/6/1 5:56 下午
 * @description
 */
@Data
public class PostVo extends Post {
    private Long authorId;
    private String authorName;
    private String authorAvatar;

    private String categoryName;
}
