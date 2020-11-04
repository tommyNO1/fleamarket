package com.zhongmingyuan.fleamarket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongmingyuan.fleamarket.cache.JedisUtil;
import com.zhongmingyuan.fleamarket.dao.ShopCategoryDao;
import com.zhongmingyuan.fleamarket.entity.ShopCategory;
import com.zhongmingyuan.fleamarket.exceptions.ShopCategoryOperationException;
import com.zhongmingyuan.fleamarket.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisString;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        //定义redis的key前缀
        String key = SCLISTKEY;
        //定义接收对象
        List<ShopCategory> shopCategoryList = null;
        //定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        //拼接出redis的key
        if(shopCategoryCondition==null){
            //若查询条件为空，则列出所有首页大类，即parentId为空的店铺类别
            key = key+"_allfirstlevel";
        }else if(shopCategoryCondition!=null&&shopCategoryCondition.getParent()!=null&&
                shopCategoryCondition.getParent().getShopCategoryId()!=null){
            //若parent为非空，则列出该parentId下的所有子类别
            key = key +"_parent"+shopCategoryCondition.getParent().getShopCategoryId();
        }else if (shopCategoryCondition!=null){
            key = key +"_allsecondlevel";
        }
        //判断key是否存在
        if(!jedisKeys.exists(key)){
            //若不存在，则从数据库里面取出相应数据
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            //将相关的实体类集合转换成String，存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new ShopCategoryOperationException(e.getMessage());
            }
            jedisString.set(key,jsonString);
        }else {
            //若存在，则直接从redis里面取出相应数据
            String jsonString = jedisString.get(key);
            //指定要将String转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,ShopCategory.class);
            //将相关key对应的value里的string转换成对象的实体类集合
            try {
                shopCategoryList = mapper.readValue(jsonString,javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new ShopCategoryOperationException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}
