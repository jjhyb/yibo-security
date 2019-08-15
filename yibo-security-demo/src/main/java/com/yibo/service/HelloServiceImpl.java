package com.yibo.service;

import org.springframework.stereotype.Service;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 1:35
 * @Description:
 */

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting(String name) {
        System.out.println("greeting");
        return "hello，"+name;
    }
}
