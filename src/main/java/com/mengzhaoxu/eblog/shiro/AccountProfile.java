package com.mengzhaoxu.eblog.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yixin
 * @date 2020/6/11 4:15 下午
 * @description
 */
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;
    private String email;
    private String sign;

    private String avatar;
    private String gender;
    private Date created;
//
//    public String getSex() {
//        return "0".equals(gender) ? "女" : "男";
//    }
}
