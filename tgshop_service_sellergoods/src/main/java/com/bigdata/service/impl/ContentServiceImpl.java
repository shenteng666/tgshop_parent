package com.bigdata.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bigdata.mapper.ContentMapper;
import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Content;
import com.bigdata.pojo.ContentExample;
import com.bigdata.service.ContentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
   
   @Autowired
   private ContentMapper contentMapper;

   @Override
   public List<Content> findAll() {
      List<Content> list = contentMapper.selectByExample(null);
      return list;
   }

   @Override
   public PageResult findPage(Content content, Integer pageNum, Integer pageSize) {
      PageHelper.startPage(pageNum, pageSize);
      Page<Content> page = (Page<Content>)contentMapper.selectByExample(null);
      return new PageResult( page.getResult(),page.getTotal());
   }

   @Override
   public void add(Content content) {
      contentMapper.insertSelective(content);
   }

   @Override
   public void delAll(Long[] ids) {
      if(ids != null){
         for(Long id : ids){
            contentMapper.deleteByPrimaryKey(id);
         }
      }
   }

    @Override
    public List<Content> findByCategoryId(Long categoryId) {
       ContentExample example = new ContentExample();
       example.createCriteria().andCategoryIdEqualTo(categoryId);
       List<Content> list = contentMapper.selectByExample(example);
       return list;
    }

    @Override
   public void edit(Content content) {
      contentMapper.updateByPrimaryKeySelective(content);
   }

   @Override
   public Content findOne(Long id) {
      Content content = contentMapper.selectByPrimaryKey(id);
      return content;
   }

}