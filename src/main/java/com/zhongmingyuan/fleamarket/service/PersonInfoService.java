package com.zhongmingyuan.fleamarket.service;

import com.zhongmingyuan.fleamarket.entity.PersonInfo;

public interface PersonInfoService {
    /*
    * 根据用户Id获取personInfo
    * @param userId
    * @return
    * */
    PersonInfo getPersonInfoById(Long userId);
}
