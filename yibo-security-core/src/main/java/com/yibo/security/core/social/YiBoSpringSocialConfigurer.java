package com.yibo.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 17:38
 * @Description:
 */
public class YiBoSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    public YiBoSpringSocialConfigurer(String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);

        if(socialAuthenticationFilterPostProcessor != null){
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T)filter;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcessor() {
        return socialAuthenticationFilterPostProcessor;
    }

    public void setSocialAuthenticationFilterPostProcessor(SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }
}
