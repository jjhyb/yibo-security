package com.yibo.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 18:06
 * @Description:
 */
public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(QQOAuth2Template.class);

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl,
                            String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);

        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters()
                .add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl,parameters,String.class);
        logger.info("获取accessToken的响应："+responseStr);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr,"&");
        String accessToken = StringUtils.substringAfterLast(items[0],"=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1],"="));
        String refreshToken = StringUtils.substringAfterLast(items[2],"=");
        return new AccessGrant(accessToken,null,refreshToken,expiresIn);
    }
}
