package com.yibo.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yibo.security.core.properties.LoginType;
import com.yibo.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: huangyibo
 * @Date: 2019/7/28 22:15
 * @Description:
 */

@Component("yiBoAuthenticationSuccessHandler")
public class YiBoAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(YiBoAuthenticationSuccessHandler.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 此方法在登录成功后会被调用
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");

        //如果配置的登录处理方式为异步提交，返回的是JSON
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else {
            //如果登录方式是同步表单提交，那么登录后的处理方式为跳转
            super.onAuthenticationSuccess(request,response,authentication);
        }


    }
}
