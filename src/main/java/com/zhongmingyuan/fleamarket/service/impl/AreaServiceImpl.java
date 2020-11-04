package com.zhongmingyuan.fleamarket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongmingyuan.fleamarket.cache.JedisUtil;
import com.zhongmingyuan.fleamarket.dao.AreaDao;
import com.zhongmingyuan.fleamarket.entity.Area;
import com.zhongmingyuan.fleamarket.exceptions.AreaOperationException;
import com.zhongmingyuan.fleamarket.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisString;

    @Override
    public List<Area> getAreaList() {
        //定义redis的key
        String key = AREALISTKEY;
        //定义接收对象
        List<Area> areaList = null;
        //定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        //判断key是否存在
        if (!jedisKeys.exists(key)) {
            //若不存在，则从数据库里面取出相应的数据
            areaList = areaDao.queryArea();
            //将相关的实体类集合转换成json，存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new AreaOperationException(e.getMessage());
            }
            jedisString.set(key, jsonString);
        } else {
            //若存在，则直接从redis里面取出相应数据
            String jsonSting = jedisString.get(key);
            //指定要将String转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Area.class);
            try {
                areaList = mapper.readValue(jsonSting, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new AreaOperationException(e.getMessage());
            }

        }
        return areaList;
    }
}
