package com.yibo.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 15:38
 * @Description:
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
