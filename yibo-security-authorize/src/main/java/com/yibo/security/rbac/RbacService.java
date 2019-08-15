package com.yibo.security.rbac.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: huangyibo
 * @Date: 2019/8/15 14:56
 * @Description:
 */
public interface RbacService {

    /**
     *
     * @param request 当前请求的信息
     * @param authentication 当前用户的信息
     * @return 是否拥有访问的权限
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
