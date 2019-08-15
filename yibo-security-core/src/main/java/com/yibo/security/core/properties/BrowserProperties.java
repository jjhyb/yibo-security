package com.yibo.security.core.properties;

/**
 * @author: huangyibo
 * @Date: 2019/7/28 21:39
 * @Description:
 */
public class BrowserProperties {

    private String signUpUrl = "/yibo-signUp.html";

    private String signOutUrl;

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private LoginType loginType = LoginType.JSON;

    //登录页面记住我的过期时间，秒为单位，这里为1小时
    private int rememberMeSeconds = 3600;

    private SessionProperties session = new SessionProperties();

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }
}
