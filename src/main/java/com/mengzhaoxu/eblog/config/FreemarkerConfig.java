package com.mengzhaoxu.eblog.config;

import com.mengzhaoxu.eblog.template.HotsTemplate;
import com.mengzhaoxu.eblog.template.PostsTemplate;
import com.mengzhaoxu.eblog.template.TimeAgoMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    private PostsTemplate postsTemplate;
    @Autowired
    private HotsTemplate hotsTemplate;

    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postsTemplate);
        configuration.setSharedVariable("hots", hotsTemplate);
    }

}
