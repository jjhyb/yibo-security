package com.yibo.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 14:46
 * @Description:
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private Logger logger = LoggerFactory.getLogger(QQImpl.class);

    //获取用户的openId
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    //获取登录用户在QQ空间的信息，包括昵称、头像、性别及黄钻信息（包括黄钻等级、是否年费黄钻等）。
    //2.使用场景
    //此接口主要用于网站使用QQ登录时，直接拉取用户在QQ空间的昵称、头像、性别等信息，降低用户的注册成本。
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    //申请QQ登录成功后，分配给应用的appid
    private String appId;

    //用户的ID，与QQ号码一一对应
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken,String appId){
        //父类会自动帮我们处理accessToken，将accessToken作为查询参数挂在请求上
        super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        //这种写法就是将accessToken替换掉URL_GET_OPENID后面跟的%s
        String url = String.format(URL_GET_OPENID,accessToken);
        String result = getRestTemplate().getForObject(url,String.class);
        logger.info("拿到的qq返回结果为：",result);
        this.openId = StringUtils.substringBetween(result,"\"openid\":\"","\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        try {
            String url = String.format(URL_GET_USERINFO,appId,openId);
            String result = getRestTemplate().getForObject(url,String.class);
            System.out.println(result);
            logger.info("拿到的qq返回结果为：",result);
            QQUserInfo userInfo = objectMapper.readValue(result,QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取QQ用户信息失败");
        }
    }
}
