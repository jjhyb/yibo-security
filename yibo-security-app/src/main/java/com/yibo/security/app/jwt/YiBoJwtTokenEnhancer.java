package com.yibo.security.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 19:38
 * @Description:
 *
 * 增强jwt返回accessToken信息的处理类
 */
public class YiBoJwtTokenEnhancer implements TokenEnhancer {

    /**
     * 将自定义的信息添加到accessToken中
     * @param accessToken
     * @param oAuth2Authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String,Object> info = new HashMap<>();
        info.put("company","com.yibo");
        //设置附加信息
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
