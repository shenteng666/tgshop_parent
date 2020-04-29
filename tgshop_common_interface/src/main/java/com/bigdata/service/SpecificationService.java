package com.bigdata.service;

import com.bigdata.mypojo.MySpecification;
import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Specification;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/24 10:20
 * @description
 */
public interface SpecificationService {
    PageResult search(int page, int rows, Specification specification);

    void add(MySpecification mySpecification);

    MySpecification findOne(Long id);

    void update(MySpecification mySpecification);

    void delete(Long[] ids);

    List<Map> selectOptionList();

}
