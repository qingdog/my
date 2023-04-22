// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   LoginServlet.java

package com.iflytek.stumanager.servlet;

import com.iflytek.stumanager.business.AdminService;
import com.iflytek.stumanager.po.Admin;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@MultipartConfig
public class LoginServlet extends HttpServlet
{

    public LoginServlet()
    {
        adminService = new AdminService();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        /* 允许跨域的主机地址 */
        response.setHeader("Access-Control-Allow-Origin", "*");//解决浏览器的同源策略问题
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        response.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        response.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        response.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String account = request.getParameter("account");
        String password = request.getParameter("password");
        System.out.println(account+"    "+password);
        Admin admin = new Admin(account, password);
        boolean legal = adminService.legal(admin);
        if(legal)
        {
            request.getSession().setAttribute("admin", admin);
            response.sendRedirect("admin/stuServlet?param=list&pageNo=1");

            response.getWriter().write("success");////
        } else
        {
            request.getRequestDispatcher("admin/error.jsp").forward(request, response);
        }
    }

    private static final long serialVersionUID = 1L;
    private AdminService adminService;
}
