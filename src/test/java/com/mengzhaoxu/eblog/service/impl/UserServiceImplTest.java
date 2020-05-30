package com.mengzhaoxu.eblog.service.impl;

import com.mengzhaoxu.eblog.entity.User;
import com.mengzhaoxu.eblog.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest extends TestCase {


    @Autowired
    private UserService userService;


    @Test
    public void tes1(){

        List<User> list = userService.list();
        System.out.println(list);

    }





}