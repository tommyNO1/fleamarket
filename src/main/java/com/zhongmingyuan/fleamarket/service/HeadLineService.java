package com.zhongmingyuan.fleamarket.service;

import com.zhongmingyuan.fleamarket.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
    public static final String HLLISTKEY = "headlinelist";
    /*
    * 根据传入条件返回指定的头条列表
    * @param headLineCondition
    * @return
    * @throws IOException
    *
    * */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
