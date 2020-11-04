package com.zhongmingyuan.fleamarket.service;

import com.zhongmingyuan.fleamarket.entity.Area;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AreaServiceTest{
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("南苑",areaList.get(0).getAreaName());
        cacheService.removeFromCache(areaService.AREALISTKEY);
        areaService.getAreaList();
    }
}
