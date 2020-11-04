package com.zhongmingyuan.fleamarket.service;

import com.zhongmingyuan.fleamarket.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    public static final String SCLISTKEY = "shopcategorylist";

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
