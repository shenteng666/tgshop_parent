package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.mypojo.MyGoods;
import com.bigdata.mypojo.PageResult;
import com.bigdata.mypojo.Result;
import com.bigdata.pojo.Goods;
import com.bigdata.service.GoodsService;
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


    //localhost:9102/goods/search.do?page=1&rows=10
    @RequestMapping("/search")
    public PageResult search(@RequestBody Goods goods, int page, int rows  ){
        PageResult pageResult = goodsService.findPage(goods, page, rows);
        return pageResult;
    }

    //http://localhost:9102/goods/findOne.do?id=149187842867964
    @RequestMapping("findOne")
    public MyGoods findOne(Long id){
        return goodsService.findOne(id);
    }
    //http://localhost:9101/goods/updateStatus.do?ids=149187842867962,149187842867963,149187842867964&status=1
    @RequestMapping("updateStatus")
    public Result updateStatus(Long[] ids,String status){
        try {
            for (Long id : ids) {
                goodsService.updateStatus(id,status);
            }
            return new Result(true,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"fail");
        }
    }

    //http://localhost:9101/goods/delete.do?ids=149187842867913,149187842867914
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try {
            goodsService.delete(ids);
            return new Result(true,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"fail");
        }

    }


}
