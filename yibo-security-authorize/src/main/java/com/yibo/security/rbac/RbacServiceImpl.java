package com.yibo.security.rbac.service.impl;

import com.yibo.security.rbac.service.RbacService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: huangyibo
 * @Date: 2019/8/15 14:59
 * @Description:
 */

@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     *
     * @param request 当前请求的信息
     * @param authentication 当前用户的信息
     * @return 是否拥有访问的权限
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        //principal可能是一个匿名的Anonymous
        // 也可能是一个经过身份认证了的UserDetails
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            //去数据库中读取用户所拥有权限的所有URL
            //在这里使用Set模拟
            Set<String> urls = new HashSet<>();

            for (String url : urls) {
                //因为请求路径有可能是/user/1，但数据库里面的Url可能是/user/*，所以使用antPathMatcher进行匹配
                if(antPathMatcher.match(url,request.getRequestURI())){
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
