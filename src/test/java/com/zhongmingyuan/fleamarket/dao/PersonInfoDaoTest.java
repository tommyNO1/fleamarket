package com.zhongmingyuan.fleamarket.dao;

import com.zhongmingyuan.fleamarket.entity.PersonInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PersonInfoDaoTest {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void testInsertPersonInfo() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("小鸣");
        personInfo.setGender("1");
        personInfo.setEmail("testEmail");
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setProfileImg("textProfile");
        personInfo.setUserType(1);
        personInfo.setEnableStatus(1);
        int effectNum = personInfoDao.insertPersonInfo(personInfo);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQueryPersonInfoById() {
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(8);
        System.out.println(personInfo.getName());
    }


}
