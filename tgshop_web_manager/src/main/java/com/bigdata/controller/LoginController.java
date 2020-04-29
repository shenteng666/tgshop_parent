package com.bigdata.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/25 11:31
 * @description
 */
@RestController
@RequestMapping("login")
public class LoginController {
    //../login/showName.do


    @RequestMapping("showName")
    public Map<String,String> showName(){
        //1.获取当前登录的用户名
        String usernmae = SecurityContextHolder.getContext().getAuthentication().getName();
        //2.把用户名放到map中并返回
        HashMap<String, String> map = new HashMap<>();
        map.put("username",usernmae);
        return map;
    }
}
