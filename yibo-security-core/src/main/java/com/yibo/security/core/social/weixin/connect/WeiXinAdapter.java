package com.yibo.security.core.social.weixin.connect;

import com.yibo.security.core.social.weixin.api.WeiXin;
import com.yibo.security.core.social.weixin.api.WeiXinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 22:43
 * @Description:
 *
 * 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 */
public class WeiXinAdapter implements ApiAdapter<WeiXin> {

    private String openId;

    public WeiXinAdapter() {}

    public WeiXinAdapter(String openId){
        this.openId = openId;
    }

    /**
     * @param api
     * @return
     */
    @Override
    public boolean test(WeiXin api) {
        return true;
    }

    /**
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(WeiXin api, ConnectionValues values) {
        WeiXinUserInfo userInfo = api.getUserInfo(openId);
        values.setProviderUserId(userInfo.getOpenid());
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadimgurl());
    }

    /**
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(WeiXin api) {
        return null;
    }

    /**
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(WeiXin api, String message) {
        //do nothing
    }

}

