package com.zhongmingyuan.fleamarket.dao;

import com.zhongmingyuan.fleamarket.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    /*
    * 根据条件筛选出相关的商铺类别
    * @param shopCategoryCondition
    * @return
    * */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
