package com.yibo.security.core.authentication.mobile;

import com.yibo.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: huangyibo
 * @Date: 2019/7/31 1:07
 * @Description:
 *
 * SmsCodeAuthenticationFilter用于拦截短信登录请求
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 在请求中携带参数的名字是telephone
     */
    public static final String YIBO_FORM_TELEPHONE_KEY = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
    private String telephoneParameter = YIBO_FORM_TELEPHONE_KEY;

    //postOnly表示此过滤器只处理post请求
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        //表示当前过滤器处理的请求是什么
        //AntPathRequestMatcher表示请求的匹配器
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
    }

    /**
     * 真正的认证流程
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //判断请求是否是POST请求
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String telephone = this.obtainTelephone(request);
            if (telephone == null) {
                telephone = "";
            }


            telephone = telephone.trim();
            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(telephone);

            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取手机号
     * @param request
     * @return
     */
    protected String obtainTelephone(HttpServletRequest request) {
        return request.getParameter(this.telephoneParameter);
    }

    /**
     * 将请求的信息设置到SmsCodeAuthenticationToken中去
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setTelephoneParameter(String telephoneParameter) {
        Assert.hasText(telephoneParameter, "telephone parameter must not be empty or null");
        this.telephoneParameter = telephoneParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getTelephoneParameter() {
        return this.telephoneParameter;
    }
}

