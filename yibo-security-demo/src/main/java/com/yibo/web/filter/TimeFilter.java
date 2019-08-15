package com.yibo.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 11:55
 * @Description:
 */

//@Component
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("time filter start");
        long start = new Date().getTime();
        filterChain.doFilter(request,response);
        System.out.println("time filter 总耗时："+(new Date().getTime() - start));
        System.out.println("time filter finish");
    }

    @Override
    public void destroy() {
        System.out.println("time filter destroy");
    }
}
