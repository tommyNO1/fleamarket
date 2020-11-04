package com.zhongmingyuan.fleamarket.web.wechat;

import com.zhongmingyuan.fleamarket.dto.UserAccessToken;
import com.zhongmingyuan.fleamarket.dto.WechatUser;
import com.zhongmingyuan.fleamarket.util.wechat.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("wechatlogin")
/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=您的appId&redirect_uri=http://o2o.yitiaojieinfo.com/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 *
 * @author xiangze
 *
 */

public class WechatLoginController {
    private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);

    @RequestMapping(value = "/logincheck", method = {RequestMethod.GET})
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("wechat login get...");
        //获取微信公众号传过来的code，通过code可获取access_token
        String code = request.getParameter("code");
        log.debug("wechat login code:" + code);
        WechatUser user = null;
        String openId = null;
        if (null != code) {
            UserAccessToken token;
            try {
                token = WechatUtil.getUserAccessToken(code);
                log.debug("wechat login token:" + token.toString());
                //通过token获取accessToken
                String accessToken = token.getAccessToken();
                //通过token获取openid
                openId = token.getOpenId();
                //通过accessToken和openid获取用户信息
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("wechat login user" + user.toString());
                request.getSession().setAttribute("openId", openId);
            } catch (IOException e) {
                log.error("error in getUserAccessToken or getUserInfo");
                e.printStackTrace();
            }

        }
        // ======todo begin======
        // 前面咱们获取到openId后，可以通过它去数据库判断该微信帐号是否在我们网站里有对应的帐号了，
        // 没有的话这里可以自动创建上，直接实现微信与咱们网站的无缝对接。
        // ======todo end======
        if (user != null) {
            // 获取到微信验证的信息后返回到指定的路由（需要自己设定）
            return "frontend/index";
        } else {
            return null;
        }
    }

}
