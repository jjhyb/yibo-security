package com.yibo.security.app;

import com.yibo.security.core.social.YiBoSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 16:43
 * @Description:
 */

@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    /**
     * spring容器中所有的bean初始化之前调用此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * spring容器中所有的bean初始化之后调用此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //在使用app模块的时候，在SocialConfig对YiBoSpringSocialConfigurer的初始化之后，调用此方法改掉signupUrl
        if(StringUtils.equals(beanName,"yiboSocialSecurityConfig")){
            YiBoSpringSocialConfigurer configurer = (YiBoSpringSocialConfigurer)bean;
            //在app环境下，在用户注册的时候，最终都会跳转到"/social/signUp"地址上去
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
