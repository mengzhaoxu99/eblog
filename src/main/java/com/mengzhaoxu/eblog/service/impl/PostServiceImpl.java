package com.mengzhaoxu.eblog.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.mapper.PostMapper;
import com.mengzhaoxu.eblog.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengzhaoxu.eblog.util.RedisKeys;
import com.mengzhaoxu.eblog.util.RedisUtil;
import com.mengzhaoxu.eblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengzhaoxu
 * @since 2020-06-01
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {

        if (level==null) level=-1;
        QueryWrapper<Post> wrapper = new QueryWrapper<Post>()
                .eq(categoryId != null, "category_id", categoryId)
                .eq(userId != null, "user_id", userId)
                .eq(level == 0, "level", 0)
                .gt(level > 0, "level", 0)
                .orderByDesc(order != null, order);
        return postMapper.selectPosts(page,wrapper);
    }


    @Override
    public PostVo selectById(Long id) {
        PostVo postVo = postMapper.selectOnePost(new QueryWrapper<Post>().eq("p.id", id));
        return postVo;
    }


    /**
     *
     *  本周热议
     */
    @Override
    public void initWeekRank() {
        //1、获取7天内发步过的文章
        QueryWrapper<Post> wrapper = new QueryWrapper<Post>()
                .ge("created", DateUtil.lastWeek())
                .select("id,title,user_id,comment_count,view_count,created");
        List<Post> posts = this.list(wrapper);
        //2、初始化文章的总评论量
        for (Post post : posts) {
            String key = RedisKeys.getPost(DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT));
            redisUtil.zSet(key,post.getId(),post.getCommentCount());
            //7天后自动过期
            long day = DateUtil.betweenDay(new Date(), post.getCreated(), false);
            long expireTime = (7-day)*24*60*60;
            redisUtil.expire(key,expireTime);
            //缓存文章的基本信息（id,标题，评论数量，作者）
            this.hashCachePostIdAndTitle(post,expireTime);
        }

        //3、作并集
        this.zunionAndStoreLastWeekForRank();



    }

    /**
     * 本周评论并集
     */
    private void zunionAndStoreLastWeekForRank() {
        String destKey = RedisKeys.getPost(DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT));
        String key = RedisKeys.getWeekRank("");
        ArrayList<String > otherKeys = new ArrayList<>();
        for (int i = -6; i < 0; i++) {

            String tempKey =RedisKeys.getPost(DateUtil.format(DateUtil.offsetDay(new Date(),i),DatePattern.PURE_DATE_FORMAT));
            otherKeys.add(tempKey);
        }

        redisUtil.zUnionAndStore(destKey,otherKeys,key);

    }

    /**
     * //缓存文章
     * @param post
     * @param expireTime
     */
    private void hashCachePostIdAndTitle(Post post, long expireTime) {

        String rankKey = RedisKeys.getRankPost(post.getId() + "");


        if (!redisUtil.hasKey(rankKey)){
            redisUtil.hset(rankKey,"post:id",post.getId(),expireTime);
            redisUtil.hset(rankKey,"post:title",post.getTitle(),expireTime);
            redisUtil.hset(rankKey,"post:commentCountd",post.getCommentCount(),expireTime);
        }

    }
}
