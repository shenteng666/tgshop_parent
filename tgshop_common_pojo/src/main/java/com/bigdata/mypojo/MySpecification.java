package com.bigdata.mypojo;

import com.bigdata.pojo.Specification;
import com.bigdata.pojo.SpecificationOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author caowei
 * @date 2019/1/24 10:46
 * @description
 */
//自定义MySpecification类用来接收提交过来的json
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MySpecification implements Serializable {
   private Specification specification;
   private List<SpecificationOption> specificationOptionList;
}
