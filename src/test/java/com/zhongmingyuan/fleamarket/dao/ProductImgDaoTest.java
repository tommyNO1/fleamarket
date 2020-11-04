package com.zhongmingyuan.fleamarket.dao;


import com.zhongmingyuan.fleamarket.entity.ProductImg;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ProductImgDaoTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABatchInsertProductImg() throws Exception {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("图片1描述");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(2L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("图片2描述");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(2L);
        ProductImg productImg3 = new ProductImg();
        productImg3.setImgAddr("图片3");
        productImg3.setImgDesc("图片3描述");
        productImg3.setPriority(1);
        productImg3.setCreateTime(new Date());
        productImg3.setProductId(2L);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        productImgList.add(productImg3);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(3, effectedNum);
    }

}
