package com.bigdata.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.bigdata.mapper.*;
import com.bigdata.mypojo.MyGoods;
import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.*;
import com.bigdata.service.GoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/27 10:27
 * @description
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private SellerMapper sellerMapper;


    /**
     * 增加
     */
    @Override
    public void add(MyGoods myGoods) {
        //1. 添加商品对象-SPU
        //初始化商品状态为0, 未审核
        myGoods.getGoods().setAuditStatus("0");
        goodsMapper.insertSelective(myGoods.getGoods());
        //2. 添加商品详情对象-SPU详情
        myGoods.getGoodsDesc().setGoodsId(myGoods.getGoods().getId());
        goodsDescMapper.insertSelective(myGoods.getGoodsDesc());
        //3. 添加SKU对象-SKU
        addItemList(myGoods);

    }

    @Override
    public PageResult findPage(Goods goods, int page, int rows) {
        PageHelper.startPage(page, rows);
        GoodsExample example = new GoodsExample();
        if (goods != null) {
            GoodsExample.Criteria criteria = example.createCriteria();
            //根据状态查询
            if(goods.getAuditStatus() != null && !"".equals(goods.getAuditStatus())){
                criteria.andAuditStatusEqualTo(goods.getAuditStatus());
            }
            //根据名称查询
            if(goods.getGoodsName() != null && !"".equals(goods.getGoodsName())){
                criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
            }
            //根据用户查询
            if(goods.getSellerId() != null && !"".equals(goods.getSellerId()) && !"admin".equals(goods.getSellerId())){
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
        }
        Page<Goods> pageList = (Page<Goods>) goodsMapper.selectByExample(example);
        return new PageResult(pageList.getResult(),pageList.getTotal());
    }

    @Override
    public MyGoods findOne(Long id) {
        //1.先查SPU
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        //2.再查SPU详情
        GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        //3.再查SKU
        ItemExample example = new ItemExample();
        example.createCriteria().andGoodsIdEqualTo(id);
        List<Item> itemList = itemMapper.selectByExample(example);
        //4.组合返回MyGoods
        MyGoods myGoods = new MyGoods(goods, goodsDesc, itemList);
        return myGoods;
    }

    @Override
    public void update(MyGoods myGoods) {
        //1.先改SPU
        goodsMapper.updateByPrimaryKeySelective(myGoods.getGoods());
        //2.再改SPU详情
        goodsDescMapper.updateByPrimaryKeySelective(myGoods.getGoodsDesc());
        //3.先删SKU
        ItemExample example = new ItemExample();
        example.createCriteria().andGoodsIdEqualTo(myGoods.getGoods().getId());
        itemMapper.deleteByExample(example);
        //4.再重新添加SKU
        addItemList(myGoods);
    }

    @Override
    public void updateStatus(Long id, String status) {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setAuditStatus(status);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            Goods goods = new Goods();
            goods.setId(id);
            goods.setIsDelete("1");
            goodsMapper.updateByPrimaryKeySelective(goods);
        }

    }


    /**
     * 添加SKU数据
     * @param myGoods
     */
    public void addItemList(MyGoods myGoods) {
        //启用规格
        String isEnableSpec = "1";
        if(isEnableSpec.equals(myGoods.getGoods().getIsEnableSpec())){
            for(Item item : myGoods.getItemList()){
                //获取页面传入的规格json字符串
                //总结
                //要转换的JSON为[]数组是:[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
                //json.parseAarray(json字符串, 转换成的list的泛型的类型)
                //将json数据转成list, 要求json字符串一定是[]数组形式
                //要转换的JSON为{}对象格式:{"机身内存":"16G","网络":"双卡"}
                //json.parseObject(json字符串, 转换成的对象的类型)
                //将json数据转换成Java对象, 要求json字符串是{}
                Map<String, String> specMap = JSON.parseObject(item.getSpec(), Map.class);
                String title = myGoods.getGoods().getGoodsName();
                //标题由商品名称 + 规格组成(在商品名称后拼接上规格, 为了全文检索, 查询精确)
                if (specMap != null) {
                    for(Map.Entry<String, String> entry: specMap.entrySet()){
                        title += " " + entry.getValue();
                    }
                }
                //SKU标题
                item.setTitle(title);
                //SKU数据状态, 默认设置成0, 未审核状态
                item.setStatus("0");
                //调用方法设置SKU对象的其他值
                item = setItemValue(myGoods, item);
                itemMapper.insertSelective(item);
            }
        } else {
            //初始化一条规格数据
            Item item = new Item();
            //初始化SKU标题
            item.setTitle(myGoods.getGoods().getGoodsName());
            //初始化状态为1已审核
            item.setStatus("1");
            //初始化规格数据, 为一个空的json串
            item.setSpec("{}");
            //初始化售价
            item.setPrice(new BigDecimal("9999"));
            //初始化SKU数量
            item.setNum(0);
            //设置SKU对象的其他值
            item = setItemValue(myGoods, item);
            itemMapper.insertSelective(item);
        }
    }
    /**
     * 设置SKU对象属性值
     * 设置冗余信息:
     * 目的:空间换时间,冗余换效率
     * @param myGoods  商品封装对象, 里面包含商品, 商品详情和商品基本数据
     * @param item        SKU数据
     */
    public Item setItemValue(MyGoods myGoods, Item item) {
        //品牌名称
        Brand brand = brandMapper.selectByPrimaryKey(myGoods.getGoods().getBrandId());
        item.setBrand(brand.getName());
        //分类id
        item.setCategoryid(myGoods.getGoods().getCategory3Id());
        //分类名称
        ItemCat itemCat3 = itemCatMapper.selectByPrimaryKey(myGoods.getGoods().getCategory3Id());
        item.setCategory(itemCat3.getName());
        //商品id
        item.setGoodsId(myGoods.getGoods().getId());
        //商品图片, 默认取第一张图片作为样例图片
        String itemImages = myGoods.getGoodsDesc().getItemImages();
        List<Map> maps = JSON.parseArray(itemImages, Map.class);
        if(maps != null && maps.size() > 0){
            String url = (String)maps.get(0).get("url");
            item.setImage(url);
        }
        //商家id
        item.setSellerId(myGoods.getGoods().getSellerId());
        //商家名称
        Seller seller = sellerMapper.selectByPrimaryKey(myGoods.getGoods().getSellerId());
        item.setSeller(seller.getName());
        //更新时间
        item.setUpdateTime(new Date());
        //创建时间
        item.setCreateTime(new Date());
        return item;
    }
}









