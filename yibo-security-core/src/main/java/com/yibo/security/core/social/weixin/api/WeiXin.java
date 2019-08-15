package com.yibo.security.core.social.weixin.api;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 22:19
 * @Description:
 *
 * 微信API调用接口
 */
public interface WeiXin {

    WeiXinUserInfo getUserInfo(String openId);
}
