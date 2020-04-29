package com.bigdata.service;

import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Seller;

/**
 * @author caowei
 * @date 2019/1/25 14:26
 * @description
 */
public interface SellerService {
    void add(Seller seller);

    PageResult findPage(Seller seller, Integer page, Integer rows);

    Seller findOne(String id);

    void updateStatus(String sellerId, String status);
}
