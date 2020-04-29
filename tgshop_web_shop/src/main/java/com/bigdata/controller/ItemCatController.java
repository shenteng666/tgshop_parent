package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.pojo.ItemCat;
import com.bigdata.service.ItemCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author caowei
 * @date 2019/1/26 11:27
 * @description
 */
@RestController
@RequestMapping("itemCat")
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;

    //itemCat/findByParentId.do?parentId=0

    @RequestMapping("findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }

    ///itemCat/findOne.do?id='+id
    @RequestMapping("findOne")
    public ItemCat findOne(Long id){
        return itemCatService.findOne(id);
    }

    @RequestMapping("/findAll")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }
}
