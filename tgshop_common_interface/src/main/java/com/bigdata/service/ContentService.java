package com.bigdata.service;

import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Content;

import java.util.List;

public interface ContentService {

   public List<Content> findAll();
   
   public PageResult findPage(Content content, Integer pageNum, Integer pageSize);
   
   public void add(Content content);
   
   public void edit(Content content);
   
   public Content findOne(Long id);
   
   public void delAll(Long[] ids);

    List<Content> findByCategoryId(Long categoryId);
}