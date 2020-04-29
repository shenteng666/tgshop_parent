package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.mypojo.PageResult;
import com.bigdata.mypojo.Result;
import com.bigdata.pojo.Seller;
import com.bigdata.service.SellerService;
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
    @RequestMapping("/search")
    public PageResult search(@RequestBody Seller seller, Integer page, Integer rows) throws Exception {
        PageResult pageResult = sellerService.findPage(seller, page, rows);
        return pageResult;
    }

    @RequestMapping("/findOne")
    public Seller findOne(String id) throws Exception {
        Seller seller = sellerService.findOne(id);
        return seller;
    }

    @RequestMapping("/updateStatus")
    public Result updateStatus(String sellerId, String status) throws Exception {
        try {
            sellerService.updateStatus(sellerId, status);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }
}
