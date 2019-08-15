package com.yibo.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yibo.security.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: huangyibo
 * @Date: 2019/8/12 15:49
 * @Description:
 *
 * 退出成功处理器，用户退出后进行日志的记录,，以及用户在什么设备，什么地点，什么时间退出进行数据记录
 */
public class YiBoLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(YiBoLogoutSuccessHandler.class);

    private String  signOutUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public YiBoLogoutSuccessHandler(String  signOutUrl){
        this.signOutUrl = signOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //这里用一句日志简单进行用户退出数据的记录
        logger.info("退出成功");
        //这里可以进行用户退出成功以后的处理逻辑

        //如果signOutUrl为空，直接返回json数据
        if(StringUtils.isBlank(signOutUrl)){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        }else {
            //如果signOutUrl不为空，直接跳转到signOutUrl页面
            response.sendRedirect(signOutUrl);
        }
    }
}
