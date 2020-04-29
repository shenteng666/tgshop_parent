package com.bigdata.service;

import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Brand;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/22 10:24
 * @description
 */
public interface BrandService {
    List<Brand> findAll();

    PageResult findPage(int pageNum, int pageSize);

    void add(Brand brand);

    Brand findOne(Long id);

    void update(Brand brand);

    void delete(Long[] ids);

    PageResult search(int pageNum, int pageSize, Brand brand);

    List<Map> selectOptionList();

}
