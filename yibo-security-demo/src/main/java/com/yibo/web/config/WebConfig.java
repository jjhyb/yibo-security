package com.yibo.web.config;

import com.yibo.web.filter.TimeFilter;
import com.yibo.web.interceptor.TimeInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 12:07
 * @Description:
 *
 * 这种方式加载和直接在TimeFilter类上面添加@Component效果一样
 *
 * 集成第三方Filter,如果第三方Filter没有@Component注解的话，就需要用这种方式
 */


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public TimeInterceptor getInterceptor(){
        return new TimeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getInterceptor());
        super.addInterceptors(registry);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        //异步处理的拦截器，需要这样配置
        //configurer.registerCallableInterceptors();
        //configurer.registerDeferredResultInterceptors();
        super.configureAsyncSupport(configurer);
    }

    @Bean
    public TimeFilter getFilter(){
        return new TimeFilter();
    }

    @Bean
    public FilterRegistrationBean timeFilter() {

        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(getFilter());

        List<String> urls = new ArrayList<>();
        urls.add("/*");

        registration.setUrlPatterns(urls);

//        registration.setOrder(1);

        return registration;

    }

}

