package com.zhongmingyuan.fleamarket.service;

import com.zhongmingyuan.fleamarket.entity.Area;

import java.util.List;

public interface AreaService {
    public static final String AREALISTKEY = "arealist";

    List<Area> getAreaList();
}
