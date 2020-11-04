package com.zhongmingyuan.fleamarket.service.impl;

import com.zhongmingyuan.fleamarket.cache.JedisUtil;
import com.zhongmingyuan.fleamarket.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromCache(String keyPrefix) {
        Set<String> keySet = jedisKeys.keys(keyPrefix+"*");
        for(String key:keySet){
            jedisKeys.del(key);
        }
    }
}
