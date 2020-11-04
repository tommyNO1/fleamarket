package com.zhongmingyuan.fleamarket.dao;

import com.zhongmingyuan.fleamarket.entity.Area;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest
public class AreaDaoTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea() {
        List<Area> areaList = areaDao.queryArea();
        assertEquals("testNum", 3, areaList.size());
        System.out.println(areaList.get(0).getAreaName());
    }
}
