package com.zhongmingyuan.fleamarket.service;

import com.zhongmingyuan.fleamarket.dto.WechatAuthExecution;
import com.zhongmingyuan.fleamarket.entity.WechatAuth;
import com.zhongmingyuan.fleamarket.exceptions.WechatAuthOperationException;

public interface WechatAuthService {
    /*
    * 通过openId查找平台对应的微信账号
    *
    * @param openId
    * @return
    * */
    WechatAuth getWechatAuthByOpenId(String openId);

    /*
    * 注册本平台的微信账号
    * @param wechatAuth
    * @param profileImg
    * @return
    * */
    WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;

}
