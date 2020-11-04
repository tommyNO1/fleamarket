package com.zhongmingyuan.fleamarket.dao;


import com.zhongmingyuan.fleamarket.entity.Product;
import com.zhongmingyuan.fleamarket.entity.ProductCategory;
import com.zhongmingyuan.fleamarket.entity.ProductImg;
import com.zhongmingyuan.fleamarket.entity.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductDaoTest {
    @Autowired
    private ProductDao productDao;

    @Autowired
    ProductImgDao productImgDao;

    @Test
    public void testAInsertProduct() throws Exception {
        Shop shop1 = new Shop();
        shop1.setShopId(7L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);
        Product product1 = new Product();
        product1.setProductName("test1");
        product1.setProductDesc("testDesc1");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(productCategory);

        Product product2 = new Product();
        product2.setProductName("test2");
        product2.setProductDesc("testDesc2");
        product2.setImgAddr("test2");
        product2.setPriority(2);
        product2.setEnableStatus(1);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(productCategory);

        Product product3 = new Product();
        product3.setProductName("test3");
        product3.setProductDesc("testDesc3");
        product3.setImgAddr("test3");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(productCategory);

        int effectedNum = productDao.insertProduct(product1);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product2);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product3);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testCQueryProductByProductId() throws Exception {
        long productId = 4;
        // 初始化两个商品详情图实例作为productId为1的商品下的详情图片
        // 批量插入到商品详情图表中
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
        // 查询productId为1的商品信息并校验返回的详情图实例列表size是否为2
        Product product = productDao.queryProductById(productId);
        assertEquals(2, product.getProductImgList().size());
        // 删除新增的这两个商品详情图实例
        effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testDUpdateProduct() {
        Shop shop = new Shop();
        shop.setShopId(7l);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2l);
        Product product = new Product();
        product.setProductId(5l);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setNormalPrice("50");
        product.setPromotionPrice("49");
        product.setLastEditTime(new Date());
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testBQueryProductList() throws Exception {
        Product productCondition = new Product();
        List<Product> productList = productDao.queryProductList(productCondition, 1, 3);
        System.out.println(productList.size());
        System.out.println(productList.get(0).getProductName());
        int count = productDao.queryProductCount(productCondition);
        System.out.println(count);
        productCondition.setProductName("test");
        productList = productDao.queryProductList(productCondition, 0, 5);
        System.out.println(productList.size());
    }

    @Test
    public void testUpdateProductCategoryToNull() {
        int effectedNum = productDao.updateProductCategoryToNull(9);
        assertEquals(1, effectedNum);
    }

}
