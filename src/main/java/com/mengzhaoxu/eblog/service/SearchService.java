package com.mengzhaoxu.eblog.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.common.mq.PostMqIndexMessage;
import com.mengzhaoxu.eblog.vo.PostVo;

import java.util.List;

public interface SearchService {

    IPage search(Page page, String keyword);

    int initEsData(List<PostVo> records);

    void createOrUpdateIndex(PostMqIndexMessage message);
//
    void removeIndex(PostMqIndexMessage message);
}
