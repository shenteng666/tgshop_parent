package com.bigdata.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bigdata.mapper.ItemCatMapper;
import com.bigdata.pojo.ItemCat;
import com.bigdata.pojo.ItemCatExample;
import com.bigdata.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author caowei
 * @date 2019/1/26 11:29
 * @description
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        ItemCatExample example = new ItemCatExample();
        ItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = itemCatMapper.selectByExample(example);
        return itemCats;
    }

    @Override
    public ItemCat findOne(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return itemCatMapper.selectByExample(null);
    }
}
