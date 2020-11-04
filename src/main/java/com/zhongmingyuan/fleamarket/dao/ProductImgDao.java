package com.zhongmingyuan.fleamarket.dao;

import com.zhongmingyuan.fleamarket.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /*
     * 批量添加商品图片
     * @param productImgList
     * @return
     * */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /*
     * 删除商品图片
     * @param productId
     * @return
     * */
    int deleteProductImgByProductId(long productId);

    /*
     * 获取商品图片列表
     * @param productId
     * @return
     * */
    List<ProductImg> queryProductImgList(long productId);

}
