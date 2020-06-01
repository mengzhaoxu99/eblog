package com.mengzhaoxu.eblog.entity;

import com.mengzhaoxu.eblog.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCollection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long postId;

    private Long postUserId;


}
