package com.zhongmingyuan.fleamarket.util;

import com.zhongmingyuan.fleamarket.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static String basePath = PathUtil.getImgBasePath()+"/upload/images/item/waterMark/";
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(com.sun.imageio.plugins.common.ImageUtil.class);

    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IllegalStateException e) {
            logger.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /*
     * 处理缩略图，并返回新生成的图片相对路径
     * */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.info("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
        logger.info("thumbnail path is :" + basePath + "waterMark.png");
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "waterMark.png")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("缩略图创建失败"+e.getMessage());
        }
        return relativeAddr;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File ditPath = new File(realFileParentPath);
        if (!ditPath.exists()) {
            ditPath.mkdirs();
        }

    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getRandomFileName() {
        //获取随机的五位数
        int ranNum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + ranNum;
    }

    /*
    * storePath是文件路径还是目录路径，
    * 如果是文件路径则删除该文件
    * 如果是目录路径则删除该目录下所有的文件
    * */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File file[] = fileOrPath.listFiles();
                for(int i=0;i<file.length;i++){
                    file[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr){
        //获取不重复随机名
        String realFileName = getRandomFileName();
        //获取文件的扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路径（带文件名）
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is" + relativeAddr);
        //获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
        //调用Thumbnail生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "waterMark.png")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败："+e.toString());
        }
        return relativeAddr;
    }
}
