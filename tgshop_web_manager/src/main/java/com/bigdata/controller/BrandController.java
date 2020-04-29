package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.mypojo.PageResult;
import com.bigdata.mypojo.Result;
import com.bigdata.pojo.Brand;
import com.bigdata.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/22 10:20
 * @description
 */
//@Controller
@RestController//==@Controller+@ResponseBody
@RequestMapping("brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("findAll")
    //@ResponseBody
    public List<Brand> findAll(){
        List<Brand> list = brandService.findAll();
        return list;
    }

    /*请求URL:
         ../brand/findPage.do?page='+page+'&rows='+rows
    请求参数:
        page='+page+'&rows='+rows
    响应格式:
        row:list<brand>
        total:总记录数
    */

    @RequestMapping("findPage")
    public PageResult findPage(int page,int rows){
        PageResult pageResult =  brandService.findPage(page,rows);
        return pageResult;

    }

    /*请求URL:
        ../brand/add.do
    请求参数:
        $scope.entity
    响应格式:
        success
        message*/

    @RequestMapping("add")
    public Result add(@RequestBody Brand brand){
        try {
            brandService.add(brand);
            return new Result(true,"增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"增加失败");
        }
    }

    /*根据id查询
    url:
       ../brand/findOne.do?id='+id
    参数:
        id='+id
    返回数据格式:
        brand*/
    @RequestMapping("findOne")
    public Brand findOne(Long id){
       return brandService.findOne(id);
    }


   /* 修改
    url:
       ../brand/update.do',$scope.entity
    参数:
        $scope.entity
    返回数据格式:
        success
        message*/
   @RequestMapping("update")
    public Result update(@RequestBody Brand brand){
       try {
           brandService.update(brand);
           return new Result(true,"修改成功");
       } catch (Exception e) {
           e.printStackTrace();
           return new Result(false,"修改失败");
       }
    }


    /*根据id批量删除
    url:
       ../brand/delete.do?ids='+$scope.selectIds
    参数:
        ids='+$scope.selectIds
    返回数据格式:
        success
        message*/
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

   /* url:
        ../brand/search.do?page='+page+"&rows="+rows, $scope.searchEntity
    参数:
     page='+page+"&rows="+rows, $scope.searchEntity
    返回值:
        total
        rows*/
   @RequestMapping("search")
   public PageResult search(int page,int rows, @RequestBody Brand brand){
       return brandService.search(page,rows,brand);
   }


    /*url:
            ../brand/selectOptionList.do
    参数:
        无
    返回数据格式:
        List<Map>
        [{id:1,text:'联想'},{id:2,text:'华为'},{id:3,text:'小米'},{id:4,text:'大米'}]*/

    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }

}
