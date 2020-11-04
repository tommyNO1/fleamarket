package com.zhongmingyuan.fleamarket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongmingyuan.fleamarket.cache.JedisUtil;
import com.zhongmingyuan.fleamarket.dao.HeadLineDao;
import com.zhongmingyuan.fleamarket.entity.HeadLine;
import com.zhongmingyuan.fleamarket.exceptions.HeadLineOperationException;
import com.zhongmingyuan.fleamarket.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisString;


    @Override
    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        //定义redis的key前缀
        String key = HLLISTKEY;
        //定义接收对象
        List<HeadLine> headLineList = null;
        //定义json数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        //拼接出redis的key
        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        //判断key是否存在
        if (!jedisKeys.exists(key)) {
            //若不存在，则从数据库中取出相应的数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            //将相关的实体类集合转换成String，存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new HeadLineOperationException(e.getMessage());
            }
            jedisString.set(key, jsonString);
        } else {
            //若存在，则直接从redis中取出相应的数据
            String jsonString = jedisString.get(key);
            //指定要将string转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                //将相关key对应的value里面的String转换为对象的实体类集合
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }
}
