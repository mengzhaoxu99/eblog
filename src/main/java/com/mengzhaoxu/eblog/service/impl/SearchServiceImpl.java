package com.mengzhaoxu.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.common.mq.PostMqIndexMessage;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.entity.PostDocment;
import com.mengzhaoxu.eblog.repository.PostRepository;
import com.mengzhaoxu.eblog.service.PostService;
import com.mengzhaoxu.eblog.service.SearchService;
import com.mengzhaoxu.eblog.vo.PostVo;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yixin
 * @date 2020/6/30 3:06 下午
 * @description 搜索引擎开发
 */

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {


    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;



    @Override
    public IPage search(Page page, String keyword) {
        // 分页信息 mybatis plus的page 转成 jpa的page
        Long current = page.getCurrent() - 1;
        Long size = page.getSize();
        Pageable pageable = PageRequest.of(current.intValue(), size.intValue());

        // 搜索es得到pageData
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword,
                "title", "authorName", "categoryName");

        org.springframework.data.domain.Page<PostDocment> docments = postRepository.search(multiMatchQueryBuilder, pageable);

        // 结果信息 jpa的pageData转成mybatis plus的pageData
        IPage pageData = new Page(page.getCurrent(), page.getSize(), docments.getTotalElements());
        pageData.setRecords(docments.getContent());
        return pageData;
    }



    @Override
    public int initEsData(List<PostVo> records) {
        if(records == null || records.isEmpty()) {
            return 0;
        }

        List<PostDocment> documents = new ArrayList<>();
        for(PostVo vo : records) {
            // 映射转换
//            PostDocment postDocment = modelMapper.map(vo, PostDocment.class);
            PostDocment postDocment = new PostDocment();
            BeanUtils.copyProperties(vo,postDocment);
            documents.add(postDocment);
        }
        postRepository.saveAll(documents);
        return documents.size();
    }


    @Override
    public void createOrUpdateIndex(PostMqIndexMessage message) {
        Long postId = message.getPostId();
        PostVo postVo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", postId));
        PostDocment postDocment = new PostDocment();
        BeanUtils.copyProperties(postVo,postDocment);
        postRepository.save(postDocment);
        log.info("es 索引更新成功！ ---> {}", postDocment.toString());
    }

    @Override
    public void removeIndex(PostMqIndexMessage message) {
        Long postId = message.getPostId();
        postRepository.deleteById(postId);
        log.info("es 索引删除成功！ ---> {}", message.toString());
    }
}
