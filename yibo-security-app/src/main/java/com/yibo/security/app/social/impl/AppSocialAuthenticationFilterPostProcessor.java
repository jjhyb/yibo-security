package com.yibo.security.app.social.impl;

import com.yibo.security.core.social.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 15:50
 * @Description:
 */

@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    @Autowired
    private AuthenticationSuccessHandler yiBoAuthenticationSuccessHandler;

    /* (non-Javadoc)
     * @see com.imooc.security.core.social.SocialAuthenticationFilterPostProcessor#process(org.springframework.social.security.SocialAuthenticationFilter)
     */
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(yiBoAuthenticationSuccessHandler);
    }
}
