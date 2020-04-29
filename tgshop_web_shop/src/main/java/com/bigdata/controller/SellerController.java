package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.mypojo.Result;
import com.bigdata.pojo.Seller;
import com.bigdata.service.SellerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caowei
 * @date 2019/1/25 14:22
 * @description
 */

@RestController
@RequestMapping("seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    //../seller/add.do',entity
    @RequestMapping("add")
    public Result add(@RequestBody Seller seller){
        //拿到用户提交过来的明文信息,对密码进行加密处理
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(seller.getPassword());
        seller.setPassword(password);
        try {
            sellerService.add(seller);
            return new Result(true,"seccuss");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"fail");
        }
    }

}
