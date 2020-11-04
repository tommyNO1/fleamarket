package com.zhongmingyuan.fleamarket.service;

import com.zhongmingyuan.fleamarket.dto.WechatAuthExecution;
import com.zhongmingyuan.fleamarket.entity.PersonInfo;
import com.zhongmingyuan.fleamarket.entity.WechatAuth;
import com.zhongmingyuan.fleamarket.enums.WechatAuthStateEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WechatAuthServiceTest{
    @Autowired
    private WechatAuthService wechatAuthService;

    @Test
    public void testRegister(){
        //新增一条微信账号
        WechatAuth wechatAuth = new WechatAuth();
        PersonInfo personInfo = new PersonInfo();
        String openId = "dafahizhfdhaih";
        //给微信账号设置上用户信息，但不设置上用户Id
        //希望创建微信账号的时候自动创建用户信息
        personInfo.setCreateTime(new Date());
        personInfo.setName("TestName");
        personInfo.setUserType(1);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setOpenId(openId);
        wechatAuth.setCreateTime(new Date());
        WechatAuthExecution wechatAuthExecution = wechatAuthService.register(wechatAuth);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(),wechatAuthExecution.getState());
        //通过openId找到新增的wechatAuth
        wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
        System.out.println(wechatAuth.getPersonInfo().getName());

    }
}
