package com.iflytek.stumanager.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PermissionCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println(this.getClass() + "（servlet）过滤器初始化...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(this.getClass() + "（servlet）过滤器执行中...");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        Object obj = httpRequest.getSession().getAttribute("admin");
        if (obj != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect("/login.jsp");
        }
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass() + "（servlet）过滤器被销毁前工作...");
    }
}
