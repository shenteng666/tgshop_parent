package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.pojo.TypeTemplate;
import com.bigdata.service.TypeTemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
   
   @Reference
   private TypeTemplateService typeTemplateService;

   @RequestMapping("/findOne")
   public TypeTemplate findOne(Long id) throws Exception {
      TypeTemplate typeTemplate = typeTemplateService.findOne(id);
      return typeTemplate;
   }
   //http://localhost:9102/typeTemplate/findBySpecList.do?id=35

   /*specList:[
   text:""
   options:[optionName:""]
           ,
           ]
   List<Map<String,Object>>*/

   @RequestMapping("findBySpecList")
   public List<Map> findBySpecList(Long id){
      return typeTemplateService.findBySpecList(id);
   }

   
}