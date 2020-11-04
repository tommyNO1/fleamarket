package com.zhongmingyuan.fleamarket.service;

public interface CacheService {
    /*
    * 依据key前缀删除匹配该模式下的所有key-value
    * @param keyPrefix
    * @return
    * */

    void removeFromCache(String keyPrefix);
}
