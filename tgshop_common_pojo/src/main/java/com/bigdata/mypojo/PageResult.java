package com.bigdata.mypojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author caowei
 * @date 2019/1/23 10:45
 * @description 自己定义的用来封装分页结果的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    private List rows;
    private Long total;
}
