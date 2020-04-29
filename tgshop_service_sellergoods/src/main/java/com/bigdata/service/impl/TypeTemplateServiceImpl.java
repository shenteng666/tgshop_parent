package com.bigdata.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.bigdata.mapper.SpecificationOptionMapper;
import com.bigdata.mapper.TypeTemplateMapper;
import com.bigdata.mypojo.PageResult;
import com.bigdata.pojo.SpecificationOption;
import com.bigdata.pojo.SpecificationOptionExample;
import com.bigdata.pojo.TypeTemplate;
import com.bigdata.pojo.TypeTemplateExample;
import com.bigdata.service.TypeTemplateService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author caowei
 * @date 2019/1/25 9:22
 * @description
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;


    @Override
    public PageResult search(int pageNum, int pageSize, TypeTemplate typeTemplate) {
        PageHelper.startPage(pageNum,pageSize);
        TypeTemplateExample example = new TypeTemplateExample();
        if (typeTemplate!= null ){
            TypeTemplateExample.Criteria criteria = example.createCriteria();
            if (typeTemplate.getName()!= null && typeTemplate.getName().length() > 0){
                criteria.andNameLike("%"+typeTemplate.getName()+"%");
            }
        }
        Page<TypeTemplate> page = (Page<TypeTemplate>) typeTemplateMapper.selectByExample(example);
        PageResult pageResult = new PageResult(page.getResult(), page.getTotal());
        return pageResult;
    }

    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateMapper.insertSelective(typeTemplate);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.deleteByPrimaryKey(id);
        }
    }

    /*
    前台需要的:
    specList:
        [
            {
           text:""
           options:[{optionName:""}]
            },
        ]
     我们现在拥有的:
     [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
     那么我们只需要在里面加上options:[]就可以了
     [{"id":27,"text":"网络",options:[]},{"id":32,"text":"机身内存",options:[]}]
    即我们最后返回的数据格式为:
    List<Map<String,Object>>
   */
    @Override
    public List<Map> findBySpecList(Long id) {
        TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        String specIds = typeTemplate.getSpecIds();//[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        //把List<Map>结构的json字符串直接转换成Java中的List<Map>
        //即把JSON字符串转成对象
        List<Map> list = JSON.parseArray(specIds, Map.class);
        for (Map map : list) {
            Long specid = Long.parseLong(map.get("id").toString());
            SpecificationOptionExample example = new SpecificationOptionExample();
            example.createCriteria().andSpecIdEqualTo(specid);
            List<SpecificationOption> options = specificationOptionMapper.selectByExample(example);
            map.put("options",options);
        }
        //[{"id":27,"text":"网络",options:[]},{"id":32,"text":"机身内存",options:[]}]
       /* [
            {
            "id": 32,
            "text": "机身内存"
            "options": [
                        {
                        "id": 98,
                        "optionName": "移动3G",
                        "orders": 1,
                        "specId": 27
                        },
                        ],
            }
          ]*/
        return list;
    }
}
