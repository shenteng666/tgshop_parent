package com.bigdata.service;

import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.TypeTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/25 9:21
 * @description
 */
public interface TypeTemplateService {
    PageResult search(int pageNum, int pageSize, TypeTemplate typeTemplate);

    TypeTemplate findOne(Long id);

    void add(TypeTemplate typeTemplate);

    void update(TypeTemplate typeTemplate);

    void delete(Long[] ids);

    List<Map> findBySpecList(Long id);
}
