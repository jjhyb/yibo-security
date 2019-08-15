package com.yibo.security.core.social.weixin.connect;

import com.yibo.security.core.social.weixin.api.WeiXin;
import com.yibo.security.core.social.weixin.api.WeiXinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 22:46
 * @Description:
 *
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 */
public class WeiXinServiceProvider extends AbstractOAuth2ServiceProvider<WeiXin> {

    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * @param appId
     * @param appSecret
     */
    public WeiXinServiceProvider(String appId, String appSecret) {
        super(new WeiXinOAuth2Template(appId, appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }


    /* (non-Javadoc)
     * @see org.springframework.social.oauth2.AbstractOAuth2ServiceProvider#getApi(java.lang.String)
     */
    @Override
    public WeiXin getApi(String accessToken) {
        return new WeiXinImpl(accessToken);
    }

}
