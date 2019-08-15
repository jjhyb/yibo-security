package com.yibo.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yibo.security.core.properties.LoginType;
import com.yibo.security.core.properties.SecurityProperties;
import com.yibo.security.core.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: huangyibo
 * @Date: 2019/7/28 22:49
 * @Description:
 */

@Component("yiBoAuthenticationFailureHandler")
public class YiBoAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(YiBoAuthenticationFailureHandler.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 登录失败后调用此方法
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");

        //如果配置的登录处理方式为异步提交，返回的是JSON
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        }else {
            //如果登录方式是同步表单提交，那么登录后的处理方式为跳转
            super.onAuthenticationFailure(request,response,exception);
        }
    }
}
