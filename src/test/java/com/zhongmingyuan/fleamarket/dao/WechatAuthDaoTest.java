package com.zhongmingyuan.fleamarket.dao;


import com.zhongmingyuan.fleamarket.entity.PersonInfo;
import com.zhongmingyuan.fleamarket.entity.WechatAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class WechatAuthDaoTest {

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Test
    public void testInsertWechatAuth() {
        WechatAuth wechatAuth = new WechatAuth();
        wechatAuth.setOpenId("text");
        wechatAuth.setCreateTime(new Date());
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1l);
        wechatAuth.setPersonInfo(personInfo);
        int effectNum = wechatAuthDao.insertWechatAuth(wechatAuth);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQueryWechatAuthByOpenId() {
        WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId("ovLbns-gxJHqC-UTPQKvgEuENl-E");
        System.out.println(wechatAuth.getWechatAuthId());
        System.out.println(wechatAuth.getPersonInfo().getName());
    }


}
