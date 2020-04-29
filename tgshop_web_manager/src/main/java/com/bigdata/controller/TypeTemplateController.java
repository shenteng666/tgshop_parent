package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.mypojo.PageResult;
import com.bigdata.mypojo.Result;
import com.bigdata.pojo.TypeTemplate;
import com.bigdata.service.TypeTemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caowei
 * @date 2019/1/25 9:20
 * @description
 */
@RestController
@RequestMapping("typeTemplate")
public class TypeTemplateController {
    @Reference
    private TypeTemplateService typeTemplateService;

    //../typeTemplate/search.do?page='+page+"&rows="+rows, searchEntity

    @RequestMapping("search")
    public PageResult search(int page, int rows, @RequestBody TypeTemplate typeTemplate){
        return typeTemplateService.search(page,rows,typeTemplate);
    }

    //查询实体
    //this.findOne=function(id){
    //    return $http.get('../typeTemplate/findOne.do?id='+id);
    //}
    @RequestMapping("findOne")
    public TypeTemplate findOne(Long id){
        return typeTemplateService.findOne(id);
    }

    ////增加
    //this.add=function(entity){
    //    return  $http.post('../typeTemplate/add.do',entity );
    //}
    @RequestMapping("add")
    public Result add(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.add(typeTemplate);
            return new Result(true,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"fail");
        }
    }


    ////修改
    //this.update=function(entity){
    //    return  $http.post('../typeTemplate/update.do',entity );
    //}
    @RequestMapping("update")
    public Result update(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.update(typeTemplate);
            return new Result(true,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"fail");
        }
    }

    ////删除
    //this.dele=function(ids){
    //    return $http.get('../typeTemplate/delete.do?ids='+ids);
    //}
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try {
            typeTemplateService.delete(ids);
            return new Result(true,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"fail");
        }
    }
}
