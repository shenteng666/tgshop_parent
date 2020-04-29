package com.bigdata.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bigdata.mapper.SpecificationMapper;
import com.bigdata.mapper.SpecificationOptionMapper;
import com.bigdata.mypojo.MySpecification;
import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Specification;
import com.bigdata.pojo.SpecificationExample;
import com.bigdata.pojo.SpecificationOption;
import com.bigdata.pojo.SpecificationOptionExample;
import com.bigdata.service.SpecificationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/24 10:21
 * @description
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    @Override
    public PageResult search(int pageNum, int pageSize, Specification specification) {
        PageHelper.startPage(pageNum,pageSize);
        SpecificationExample example = new SpecificationExample();
        if (specification != null){
            SpecificationExample.Criteria criteria = example.createCriteria();
            if (specification.getSpecName() != null && specification.getSpecName().trim().length() > 0){
                criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
            }
        }
        Page<Specification> page = (Page<Specification>)specificationMapper.selectByExample(example);
        PageResult pageResult = new PageResult(page.getResult(), page.getTotal());
        return pageResult;
    }

    @Override
    public void add(MySpecification mySpecification) {
        //1.保存规格数据
        //注意:需要在xml中配置:useGeneratedKeys="true" keyColumn="id" keyProperty="id"
        //表示在执行完insert语句后把数据库自动生成的id封装到JavaBean的id属性中
        specificationMapper.insert(mySpecification.getSpecification());
        //2.保存规格选项
        for (SpecificationOption specificationOption : mySpecification.getSpecificationOptionList()) {
            //注意:不要忘了设置逻辑外键值
            specificationOption.setSpecId(mySpecification.getSpecification().getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    @Override
    public MySpecification findOne(Long id) {
        //1.根据id查询Specification
        Specification specification = specificationMapper.selectByPrimaryKey(id);
        //2.根据id作为外键spec_id查询SpecificationOption
        SpecificationOptionExample example = new SpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(id);
        List<SpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);
        //3.把Specification和SpecificationOption分装成MySpecification返回
        MySpecification mySpecification = new MySpecification(specification, specificationOptionList);
        return mySpecification;
    }

    @Override
    public void update(MySpecification mySpecification) {
        //1.先更新Specification
        specificationMapper.updateByPrimaryKey(mySpecification.getSpecification());
        //2.再根据Specification的id作为外键spec_id删除specificationOption
        SpecificationOptionExample example = new SpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(mySpecification.getSpecification().getId());
        specificationOptionMapper.deleteByExample(example);
        //3.重新插入specificationOption
        for (SpecificationOption specificationOption : mySpecification.getSpecificationOptionList()) {
            specificationOption.setSpecId(mySpecification.getSpecification().getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    @Override
    public void delete(Long[] ids) {
        //1.先根据id删除Specification
        for (Long id : ids) {
            specificationMapper.deleteByPrimaryKey(id);
            //2.再根据外键删除specificationOption
            SpecificationOptionExample example = new SpecificationOptionExample();
            example.createCriteria().andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }

    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}
