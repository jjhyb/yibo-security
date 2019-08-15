package com.yibo.security.core.social.qq.connect;

import com.yibo.security.core.social.qq.api.QQ;
import com.yibo.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 15:35
 * @Description:
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试当前Api是否可用，true为可用，false为不可用
     * @param qq
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    /**
     * 在connet数据和Api数据直接做一个适配
     * @param api
     * @param connectionValues
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues connectionValues) {
        QQUserInfo userInfo = api.getUserInfo();
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        connectionValues.setProfileUrl(null);//qq没有个人主页
        connectionValues.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //do noting
    }
}
