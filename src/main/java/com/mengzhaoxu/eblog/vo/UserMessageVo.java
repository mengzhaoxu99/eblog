package com.mengzhaoxu.eblog.vo;

import com.mengzhaoxu.eblog.entity.UserMessage;
import lombok.Data;

/**
 * @author yixin
 * @date 2020/6/24 11:53 上午
 * @description
 */
@Data
public class UserMessageVo extends UserMessage {
    private String toUserName;
    private String fromUserName;
    private String postTitle;
    private String commentContent;
}
