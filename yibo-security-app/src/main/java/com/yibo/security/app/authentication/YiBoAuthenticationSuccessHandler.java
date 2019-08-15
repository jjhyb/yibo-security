package com.yibo.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yibo.security.core.properties.SecurityProperties;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
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

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

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

        String header = request.getHeader("Authorization");
        if (header == null && !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        String[] tokens = this.extractAndDecodeHeader(header, request);

        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        //读取clientId信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if(clientDetails == null){
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在：["+clientId+"]");
        }else if(!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配：["+clientId+"]");
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId,clientDetails.getScope(),"custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);

        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        //因为登录处理方式为app请求，返回的是JSON
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }

    /**
     * 对请求中的header信息进行抽取和解码
     * @param header
     * @param request
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        //因为header中的Authorization信息为Basic eWlibzp5aWJvc2VjcmV0，前6位无用，直接去掉
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            //对Authorization的信息进行Base64解码
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        //将解码信息转换成字符串
        String token = new String(decoded, "UTF-8");
        //找到字符串中的冒号的索引
        int delim = token.indexOf(":");
        if (delim == -1) {
            //如果没找到冒号，直接抛异常
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            //从字符串开始到冒号的位置为用户名，从冒号往后为密码
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
