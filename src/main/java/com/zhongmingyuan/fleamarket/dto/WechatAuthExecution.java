package com.zhongmingyuan.fleamarket.dto;

import com.zhongmingyuan.fleamarket.entity.WechatAuth;
import com.zhongmingyuan.fleamarket.enums.WechatAuthStateEnum;

import java.util.List;

public class WechatAuthExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    private int count;

    private WechatAuth wechatAuth;

    private List<WechatAuth> wechatAuthList;

    public WechatAuthExecution(){

    }

    public WechatAuthExecution(WechatAuthStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public WechatAuthExecution(WechatAuthStateEnum stateEnum,WechatAuth wechatAuth){
        this.wechatAuth = wechatAuth;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }


    public WechatAuthExecution(WechatAuthStateEnum stateEnum,List<WechatAuth> wechatAuthList){
        this.wechatAuthList = wechatAuthList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WechatAuth getWechatAuth() {
        return wechatAuth;
    }

    public void setWechatAuth(WechatAuth wechatAuth) {
        this.wechatAuth = wechatAuth;
    }

    public List<WechatAuth> getWechatAuthList() {
        return wechatAuthList;
    }

    public void setWechatAuthList(List<WechatAuth> wechatAuthList) {
        this.wechatAuthList = wechatAuthList;
    }
}
