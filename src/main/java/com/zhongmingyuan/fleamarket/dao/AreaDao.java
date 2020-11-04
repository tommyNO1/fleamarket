package com.zhongmingyuan.fleamarket.dao;

import com.zhongmingyuan.fleamarket.entity.Area;

import java.util.List;

public interface AreaDao {
    /*
     * 列出区域列表
     * @return areaList
     * */
    List<Area> queryArea();
}
