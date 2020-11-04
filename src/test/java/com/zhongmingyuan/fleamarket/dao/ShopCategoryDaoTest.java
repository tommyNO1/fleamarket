package com.zhongmingyuan.fleamarket.dao;


import com.zhongmingyuan.fleamarket.entity.ShopCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ShopCategoryDaoTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
        List<ShopCategory> shopCategoryListList = shopCategoryDao.queryShopCategory(new ShopCategory());
        assertEquals(1, shopCategoryListList.size());
    }
}
