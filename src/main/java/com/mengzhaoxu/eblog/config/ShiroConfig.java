package com.mengzhaoxu.eblog.config;

import cn.hutool.core.map.MapUtil;
import com.mengzhaoxu.eblog.shiro.AccountRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yixin
 * @date 2020/6/11 4:46 下午
 * @description
 */

@Configuration
@Slf4j
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(AccountRealm accountRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        log.info("------------------>securityManager注入成功");
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 配置登录的url和登录成功的url
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/user/center");
        // 配置未授权跳转页面
        filterFactoryBean.setUnauthorizedUrl("/error/403");

//        filterFactoryBean.setFilters(MapUtil.of("auth", authFilter()));

        Map<String, String> hashMap = new LinkedHashMap<>();
//
//        hashMap.put("/res/**", "anon");
//
        hashMap.put("/user/home", "authc");
        hashMap.put("/user/set", "authc");
        hashMap.put("/user/upload", "authc");
        hashMap.put("/user/index", "authc");
        hashMap.put("/user/public", "authc");
        hashMap.put("/user/collection", "authc");
        hashMap.put("/user/message", "authc");
        hashMap.put("/message/remove/", "authc");
        hashMap.put("/message/nums/", "authc");
//
//        hashMap.put("/collection/remove/", "auth");
//        hashMap.put("/collection/find/", "auth");
//        hashMap.put("/collection/add/", "auth");
//
//        hashMap.put("/post/edit", "auth");
//        hashMap.put("/post/submit", "auth");
//        hashMap.put("/post/delete", "auth");
//        hashMap.put("/post/reply/", "auth");
//
//        hashMap.put("/websocket", "anon");
        hashMap.put("/login", "anon");
        filterFactoryBean.setFilterChainDefinitionMap(hashMap);

        return filterFactoryBean;

    }
}
