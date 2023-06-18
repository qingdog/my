// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   EncodingFilter.java

package com.iflytek.stumanager.filter;

import java.io.IOException;
import javax.servlet.*;

public class EncodingFilter
    implements Filter
{
    private String encoding;

    public EncodingFilter()
    {
        encoding = null;
    }

    public void init(FilterConfig config)
        throws ServletException
    {
        encoding = config.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void destroy()
    {
    }


}
