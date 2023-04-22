package com.iflytek.stumanager.servlet;


import com.iflytek.stumanager.business.AdminService;
import com.iflytek.stumanager.po.Admin;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class LoginServlet extends HttpServlet {
    private final AdminService adminService;

    public LoginServlet() {
        adminService = new AdminService();
    }

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        /* 允许跨域的主机地址 */
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");//解决浏览器的同源策略问题
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        String account = httpServletRequest.getParameter("account");
        String password = httpServletRequest.getParameter("password");
        System.out.println(account + "    " + password);
        Admin admin = new Admin(account, password);
        boolean legal = adminService.legal(admin);
        if (legal) {
            httpServletRequest.getSession().setAttribute("admin", admin);
            httpServletResponse.sendRedirect("admin/stuServlet");

//            httpServletResponse.getWriter().write("success");
        } else {
            httpServletRequest.getRequestDispatcher("admin/error.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }
}
