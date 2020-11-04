package com.zhongmingyuan.fleamarket.dao;

import com.zhongmingyuan.fleamarket.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    /*
     * 添加商品
     * @Param product
     * @Return effectedNum
     * */
    int insertProduct(Product product);

    /*
     * 查询商品信息
     * @Param productId
     * @Return product
     * */
    Product queryProductById(long productId);

    /*
     * 更新商品信息
     * @Param product
     * @Return effectedNum
     * */
    int updateProduct(Product product);

    /*
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id，店铺类别
     * @param productCondition
     * @param beginIndex
     * @param pageSize
     * @return
     * */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    /*
     * 查询对应商品的总数
     * @param productCondition
     * @return
     * */
    int queryProductCount(@Param("productCondition") Product productCondition);


    /*
     * 删除商品类别之前，将商品中的product_category_id置为空
     * @param productCategoryId
     * @return
     * */
    int updateProductCategoryToNull(long productCategoryId);
}
