package com.bigdata.mypojo;

import com.bigdata.pojo.Goods;
import com.bigdata.pojo.GoodsDesc;
import com.bigdata.pojo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author caowei
 * @date 2019/1/27 10:23
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyGoods implements Serializable {
    private Goods goods; //SPU
    private GoodsDesc goodsDesc;//SPU详情
    private List<Item> itemList;//SKU
}
