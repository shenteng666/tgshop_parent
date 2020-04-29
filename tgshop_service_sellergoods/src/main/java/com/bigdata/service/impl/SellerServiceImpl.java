package com.bigdata.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bigdata.mapper.SellerMapper;
import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.Seller;
import com.bigdata.pojo.SellerExample;
import com.bigdata.service.SellerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author caowei
 * @date 2019/1/25 14:26
 * @description
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public void add(Seller seller) {
        seller.setStatus("0");//数据库中默认值就为0,表示未审核,这里也可以不设置
        seller.setCreateTime(new Date());//设置创建时间
        sellerMapper.insertSelective(seller);
    }

    @Override
    public PageResult findPage(Seller seller, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SellerExample example = new SellerExample();
        SellerExample.Criteria criteria = example.createCriteria();
        if(seller != null){
            if(seller.getNickName() != null && !"".equals(seller.getNickName())){
                criteria.andNickNameLike("%"+seller.getNickName()+"%");
            }
            if (seller.getStatus()!=null && seller.getStatus().length() > 0){
                criteria.andStatusEqualTo(seller.getStatus());
            }
        }
        Page<Seller> page = (Page<Seller>)sellerMapper.selectByExample(example);
        return new PageResult( page.getResult(),page.getTotal());
    }

    @Override
    public Seller findOne(String id) {
        return sellerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateStatus(String sellerId, String status) {
        Seller seller = new Seller();
        seller.setSellerId(sellerId);
        seller.setStatus(status);
        sellerMapper.updateByPrimaryKeySelective(seller);
    }
}
