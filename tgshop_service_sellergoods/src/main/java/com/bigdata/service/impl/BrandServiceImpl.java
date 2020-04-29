package com.bigdata.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bigdata.mapper.BrandMapper;
import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Brand;
import com.bigdata.pojo.BrandExample;
import com.bigdata.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/22 10:25
 * @description
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        List<Brand> list = brandMapper.selectByExample(null);
        return list;
    }

    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        /*List<Brand> brands = brandMapper.selectByExample(null);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        PageResult pageResult = new PageResult(pageInfo.getList(), pageInfo.getTotal());
        return pageResult;*/
        Page<Brand> page = (Page<Brand>) brandMapper.selectByExample(null);
        PageResult pageResult = new PageResult(page.getResult(), page.getTotal());
        return pageResult;
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public Brand findOne(Long id) {
        return brandMapper.selectByPrimaryKey(id);

    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult search(int pageNum, int pageSize, Brand brand) {
        //1.开启分页
        PageHelper.startPage(pageNum,pageSize);
        //2.构建查询条件
        BrandExample example = new BrandExample();
        BrandExample.Criteria criteria = example.createCriteria();
        if (brand !=null && brand.getName() !=null && brand.getName().trim().length()>0){
            criteria.andNameLike("%"+brand.getName()+"%");
        }
        if (brand !=null && brand.getFirstChar()!=null && brand.getFirstChar().trim().length() == 1){
            criteria.andFirstCharEqualTo(brand.getFirstChar().toUpperCase());
        }
        //3.查询
        Page<Brand> page = (Page<Brand>) brandMapper.selectByExample(example);
        //4.封装成pageResult
        PageResult pageResult = new PageResult(page.getResult(), page.getTotal());
        return pageResult;
    }

    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
