package com.mengzhaoxu.eblog.common.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PostMqIndexMessage implements Serializable {

    // 两种type
    public final static String CREATE_OR_UPDATE = "create_update";
    public final static String REMOVE = "remove";

    private Long postId;
    private String type;
    public PostMqIndexMessage() {
    }


}
