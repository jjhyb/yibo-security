package com.yibo.security.core.properties;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 16:27
 * @Description:
 */
public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WeiXinProperties weixin = new WeiXinProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public WeiXinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeiXinProperties weixin) {
        this.weixin = weixin;
    }
}
