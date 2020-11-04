package com.zhongmingyuan.fleamarket.service;

import com.zhongmingyuan.fleamarket.dto.LocalAuthExecution;
import com.zhongmingyuan.fleamarket.entity.LocalAuth;
import com.zhongmingyuan.fleamarket.exceptions.LocalAuthOperationException;

public interface LocalAuthService {
    /*
     * 通过账号和密码获取平台账号信息
     *
     * */
    LocalAuth getLocalAuthByUsernameAndPwd(String userName, String password);

    /*
     * 通过userId获取平台账号信息
     * */
    LocalAuth getLocalAuthByUserId(long userId);

    /*
     * 绑定微信，生成平台专属的账号
     * */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

    /*
     * 修改平台账号的登录密码
     * */
    LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword) throws
            LocalAuthOperationException;

}
