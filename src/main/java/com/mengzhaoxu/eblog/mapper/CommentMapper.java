package com.mengzhaoxu.eblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengzhaoxu.eblog.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-06-01
 */
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<CommentVo> selectComments(Page page,@Param(Constants.WRAPPER) QueryWrapper<Comment> wrapper);
}
