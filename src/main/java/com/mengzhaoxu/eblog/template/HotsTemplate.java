package com.mengzhaoxu.eblog.template;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.common.templates.DirectiveHandler;
import com.mengzhaoxu.eblog.common.templates.TemplateDirective;
import com.mengzhaoxu.eblog.service.PostService;
import com.mengzhaoxu.eblog.util.RedisKeys;
import com.mengzhaoxu.eblog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class HotsTemplate extends TemplateDirective {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getName() {
        return "hots";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {


        String key = RedisKeys.getWeekRank();
        Set<ZSetOperations.TypedTuple> zSetRank = redisUtil.getZSetRank(key, 0, 6);

        ArrayList<Map> list = new ArrayList<>();
        for (ZSetOperations.TypedTuple typedTuple : zSetRank) {
            Object value = typedTuple.getValue();//post,id
            HashMap<String, Object> map = new HashMap<>();
            String postKey = RedisKeys.getRankPost(value+"");
            map.put("id",value);
            map.put("title",redisUtil.hget(postKey,"post:title"));
            map.put("commentCount",typedTuple.getScore());
            map.put("title",redisUtil.hget(postKey,"post:title"));
            list.add(map);
        }
//        Integer level = handler.getInteger("level");
//        Integer pn = handler.getInteger("pn", 1);
//        Integer size = handler.getInteger("size", 2);
//        Long categoryId = handler.getLong("categoryId");
//        IPage page = postService.paging(new Page(pn, size), categoryId, null, level, null, "created");
//
        handler.put(RESULTS, list).render();
    }
}
