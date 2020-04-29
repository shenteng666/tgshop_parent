package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.mypojo.MySpecification;
import com.bigdata.mypojo.PageResult;
import com.bigdata.mypojo.Result;
import com.bigdata.pojo.Specification;
import com.bigdata.service.SpecificationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/24 10:16
 * @description
 */
@RestController
@RequestMapping("specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;

    /*url:
            ../specification/search.do?page='+page+"&rows="+rows, searchEntity
    参数:
        page='+page+"&rows="+rows, searchEntity
    返回值:
        rows
        total*/

    @RequestMapping("search")
    public PageResult search(int page, int rows, @RequestBody Specification specification){
        return specificationService.search(page,rows,specification);
    }
    /*url:
        specification/add.do
    参数:
        entity
            specification
                specName
            specificationOptionList
                specificationOption
                    optionName
                    orders
    返回值:
        success/flag
        message*/
    @RequestMapping("add")
    public Result add(@RequestBody MySpecification mySpecification){
        try {
            specificationService.add(mySpecification);
            return new Result(true,"增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"增加失败");
        }
    }
   /* 根据id查询
    url:
            ../specification/findOne.do
    参数:
            ?id='+id
    返回值:
        MySpecification*/

   @RequestMapping("findOne")
   public MySpecification findOne(Long id){
       return specificationService.findOne(id);
   }


    /* 更新
    url:
            ../specification/update.do',
    参数:
        entity
    返回值:
        result*/
    @RequestMapping("update")
    public Result update(@RequestBody MySpecification mySpecification){
        try {
            specificationService.update(mySpecification);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

   /* url:
            ../specification/delete.do?ids='+ids
    参数:
        ids='+ids
    返回值:
        Result*/
   @RequestMapping("delete")
   public Result delete(Long[] ids){
       try {
           specificationService.delete(ids);
           return new Result(true,"删除成功");
       } catch (Exception e) {
           e.printStackTrace();
           return new Result(false,"删除失败");
       }
   }

   //../specification/selectOptionList.do
   @RequestMapping("selectOptionList")
   public List<Map> selectOptionList(){
       return specificationService.selectOptionList();
   }

}
