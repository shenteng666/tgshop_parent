package com.bigdata.service;

import com.bigdata.pojo.ItemCat;

import java.util.List;

/**
 * @author caowei
 * @date 2019/1/26 11:29
 * @description
 */
public interface ItemCatService {
    List<ItemCat> findByParentId(Long parentId);

    ItemCat findOne(Long id);

    List<ItemCat> findAll();

}
