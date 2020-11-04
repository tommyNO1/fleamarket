package com.zhongmingyuan.fleamarket.service.impl;

import com.zhongmingyuan.fleamarket.dao.PersonInfoDao;
import com.zhongmingyuan.fleamarket.entity.PersonInfo;
import com.zhongmingyuan.fleamarket.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    private PersonInfoDao personInfoDao;


    @Override
    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.queryPersonInfoById(userId);
    }
}
