package com.zhongmingyuan.fleamarket.dao;

import com.zhongmingyuan.fleamarket.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /*
     * 获取店铺商品类别列表
     * @Param shopId
     * @Return List<ProductCategory>
     * */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /*
     * 批量新增商品类别
     * @Param productCategoryList
     * @Return
     * */
    int batchInsertProductCategory(List<ProductCategory> productCategoriesList);

    /*
     * 删除商品类别
     * @Param productCategoryId
     * @Param shopId
     * @Rerun effectedNum
     * */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
