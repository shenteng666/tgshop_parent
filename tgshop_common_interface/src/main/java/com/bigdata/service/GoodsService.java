package com.bigdata.service;

import com.bigdata.mypojo.MyGoods;
import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Goods;

/**
 * @author caowei
 * @date 2019/1/27 10:27
 * @description
 */
public interface GoodsService {
    void add(MyGoods myGoods);

    PageResult findPage(Goods goods, int page, int rows);

    MyGoods findOne(Long id);

    void update(MyGoods myGoods);

    void updateStatus(Long id, String status);

    void delete(Long[] ids);
}
