package com.zhongmingyuan.fleamarket.service.impl;

import com.zhongmingyuan.fleamarket.dao.ProductDao;
import com.zhongmingyuan.fleamarket.dao.ProductImgDao;
import com.zhongmingyuan.fleamarket.dto.ImageHolder;
import com.zhongmingyuan.fleamarket.dto.ProductExecution;
import com.zhongmingyuan.fleamarket.entity.Product;
import com.zhongmingyuan.fleamarket.entity.ProductImg;
import com.zhongmingyuan.fleamarket.enums.ProductStateEnum;
import com.zhongmingyuan.fleamarket.exceptions.ProductOperationException;
import com.zhongmingyuan.fleamarket.service.ProductService;
import com.zhongmingyuan.fleamarket.util.ImageUtil;
import com.zhongmingyuan.fleamarket.util.PageCalculator;
import com.zhongmingyuan.fleamarket.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    //1.处理缩略图，获取缩略图限度路径赋值给product
    //2.往tb_product写入商品信息，获取productId
    //3.结合productId批量处理商品详情图
    //4.将商品详情图列表批量插入tb_product_img中
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架状态
            product.setEnableStatus(1);
            //若商品缩略图不为空则进行添加
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败：" + e.getMessage());
            }
            //若商品详情图不为空则添加
            if (productImgList != null && productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            //传参数为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                          List<ImageHolder> productImgList) throws ProductOperationException {
        //1.若缩略图参数有值，则处理缩略图，若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径赋值给product
        //2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
        //3.将tb_product_img下面的该商品原先的商品详情记录全部清除
        //4.更新tb_product的信息

        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置默认属性
            product.setLastEditTime(new Date());
            //若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
            if (thumbnail != null) {
                //先获取一遍原有信息，因为原来信息里面有原图片地址
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            //如果新存入的商品详情图，则将原先的删除，并添加新的图片
            if (productImgList != null && productImgList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgList);
            }
            try {
                //更新商品信息
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("更新商品失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败"+e.getMessage());
            }
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //页码转换成数据库的行码，并调用dao层取回指定页码的商品列表
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        //基于同样的查询条件返回该查询条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution productExecution = new ProductExecution();
        productExecution.setCount(count);
        productExecution.setProductList(productList);
        return productExecution;
    }

    private void deleteProductImgList(Long productId) {
        //根据productId获取原来的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //删除原来图片
        for(ProductImg productImg:productImgList){
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库里面原有的图片信息
        productImgDao.deleteProductImgByProductId(productId);
    }

    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储路径，这里直接存放到相应店铺的文件夹地下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        //遍历图片一次去处理，并添加进productImg实体类里
        for (ImageHolder productImg : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImg, dest);
            ProductImg productImgTemp = new ProductImg();
            productImgTemp.setImgAddr(imgAddr);
            productImgTemp.setProductId(product.getProductId());
            productImgTemp.setCreateTime(new Date());
            productImgList.add(productImgTemp);
        }
        //如果确实有图片需要添加，就执行批量添加操作
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图失败" + e.getMessage());
            }
        }
    }

    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

}
