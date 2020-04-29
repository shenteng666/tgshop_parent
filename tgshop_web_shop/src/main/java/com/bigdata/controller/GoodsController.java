package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.mypojo.MyGoods;
import com.bigdata.mypojo.PageResult;
import com.bigdata.mypojo.Result;
import com.bigdata.pojo.Goods;
import com.bigdata.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caowei
 * @date 2019/1/27 10:22
 * @description
 */
@RestController
@RequestMapping("goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;

    /*http://localhost:9102/goods/add.do

    goods
    goodsDesc
    itemList

    Result*/

    @RequestMapping("add")
    public Result add(@RequestBody MyGoods myGoods){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        myGoods.getGoods().setSellerId(sellerId);
        try {
            goodsService.add(myGoods);
            return new Result(true,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"fail");
        }
    }

    //localhost:9102/goods/search.do?page=1&rows=10
    @RequestMapping("/search")
    public PageResult search(@RequestBody Goods goods, int page, int rows  ){
        //获取商家ID
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //添加查询条件
        goods.setSellerId(sellerId);
        PageResult result = goodsService.findPage(goods, page, rows);
        return result;
    }

    //http://localhost:9102/goods/findOne.do?id=149187842867964
    @RequestMapping("findOne")
    public MyGoods findOne(Long id){
        return goodsService.findOne(id);
    }

    //../goods/update.do',entity
    @RequestMapping("update")
    public Result update(@RequestBody MyGoods myGoods){
        //1.获取当前登录用户的用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //2.对比当前登录用户的用户名和页面传入进来的商品中的用户名是否相等
        if(!userName.equals(myGoods.getGoods().getSellerId())){
            return new Result(false, "您没有权限修改!");
        }
        //3.对比页面传入进来的用户名和页面传入进来的商品ID所对应的数据库中数据的用户名是否相等
        MyGoods dbMyGoods = goodsService.findOne(myGoods.getGoods().getId());
        if(!dbMyGoods.getGoods().getSellerId().equals(myGoods.getGoods().getSellerId())){
            return new Result(false, "您没有权限修改!");
        }
        try {
            goodsService.update(myGoods);
            return new Result(true,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"fail");
        }
    }
}
