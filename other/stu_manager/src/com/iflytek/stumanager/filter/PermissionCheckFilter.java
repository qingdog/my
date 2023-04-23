package com.iflytek.stumanager.filter;

import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.*;
import javax.servlet.http.*;

public class PermissionCheckFilter
        implements Filter
{

    public PermissionCheckFilter()
    {
    }

    public void init(FilterConfig filterConfig)
    {
        System.out.println("过滤器开始执行初始化功能...");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException
    {
        System.out.println("过滤器执行过滤工作123过滤器执行...");
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        Object obj = httpRequest.getSession().getAttribute("admin");
        if(obj != null)
            filterChain.doFilter(request, response);
        else
            filterChain.doFilter(request, response);////
            //httpResponse.sendRedirect("../login.jsp");
    }

    public void destroy()
    {
        System.out.println("过滤器执行销毁前工作...");
    }
}
