package com.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bigdata.pojo.Content;
import com.bigdata.service.ContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author caowei
 * @date 2019/1/28 16:02
 * @description
 */
@RestController
@RequestMapping("content")
public class ContentController {
    @Reference
    private ContentService contentService;

    //http://localhost:9103/content/findByCategoryId.do?categoryId=1

    @RequestMapping("findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId){
        List<Content>  list =  contentService.findByCategoryId(categoryId);
        return list;
    }
}
