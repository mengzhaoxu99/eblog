package com.mengzhaoxu.eblog.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yixin
 * @date 2020/5/28 10:37 上午
 * @description
 */
@Data
public class BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Date created;
    private Date modified;
}
