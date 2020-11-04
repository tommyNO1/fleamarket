package com.zhongmingyuan.fleamarket.dao;


import com.zhongmingyuan.fleamarket.entity.Area;
import com.zhongmingyuan.fleamarket.entity.PersonInfo;
import com.zhongmingyuan.fleamarket.entity.Shop;
import com.zhongmingyuan.fleamarket.entity.ShopCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ShopDaoTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopList() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1l);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表的大小" + shopList.size());
        System.out.println("店铺总数" + count);
    }

    @Test
    public void testQueryByShopId() {
        long shopId = 35;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId" + shop.getArea().getAreaId());
        System.out.println("areaName" + shop.getArea().getAreaName());
        System.out.println("areaName" + shop.getShopImg());
    }

    @Test
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺");
        shop.setShopDesc("test");
        shop.setShopDesc("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setPriority(1);
        shop.setAdvice("审核中");
        shop.setEnableStatus(1);
        shop.setCreateTime(new Date());
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setShopId(6L);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }
}
