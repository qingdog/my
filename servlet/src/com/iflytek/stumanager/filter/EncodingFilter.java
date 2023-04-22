package com.iflytek.stumanager.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter
        implements Filter {
    private String encoding;

    public EncodingFilter() {
        encoding = null;
    }

    public void init(FilterConfig config)
            throws ServletException {
        encoding = config.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
