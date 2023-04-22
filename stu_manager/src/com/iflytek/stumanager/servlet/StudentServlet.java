// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   StudentServlet.java

package com.iflytek.stumanager.servlet;

import com.iflytek.stumanager.business.ClassesService;
import com.iflytek.stumanager.business.StudentService;
import com.iflytek.stumanager.po.Classes;
import com.iflytek.stumanager.po.Student;
import com.iflytek.stumanager.util.PageModel;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@MultipartConfig
public class StudentServlet extends HttpServlet
{

    public StudentServlet()
    {
        stuService = new StudentService();
        clsService = new ClassesService();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        /* 允许跨域的主机地址 *//*
        response.setHeader("Access-Control-Allow-Origin", "*");//解决浏览器的同源策略问题
        *//* 允许跨域的请求方法GET, POST, HEAD 等 *//*
        response.setHeader("Access-Control-Allow-Methods", "*");
        *//* 重新预检验跨域的缓存时间 (s) *//*
        response.setHeader("Access-Control-Max-Age", "3600");
        *//* 允许跨域的请求头 *//*
        response.setHeader("Access-Control-Allow-Headers", "*");
        *//* 是否携带cookie *//*
        response.setHeader("Access-Control-Allow-Credentials", "true");*/

        String param = request.getParameter("param");
        System.out.println((new StringBuilder("param: ")).append(param).toString());
        int totalRecords = stuService.queryTotalRecords();
        if("list".equals(param))
        {
            int pageNo = Integer.parseInt(request.getParameter("pageNo"));
            List stuList = stuService.findStudentsByPageNo(pageNo);
            PageModel pageModel = new PageModel();
            pageModel.setStuList(stuList);
            pageModel.setTotalRecords(totalRecords);
            pageModel.setPageNo(pageNo);
            pageModel.setTotalPages((totalRecords - 1) / 5 + 1);
            request.setAttribute("pageModel", pageModel);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } else
        if("pageList".equals(param))
        {
            int pageNo = Integer.parseInt(request.getParameter("pageNo"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            List stuList = stuService.findStudentsByPageNo(pageNo,pageSize);
            JSONArray  jsonArray = new JSONArray();
            Student stu;
            for(Iterator iterator = stuList.iterator(); iterator.hasNext(); jsonArray.add(stu.toJSON()))
                stu = (Student)iterator.next();
            JSONObject json = new JSONObject();
            json.put("totalRecords", Integer.valueOf(totalRecords));
            json.put("list",jsonArray);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(json.toString());
        } else
        if("jsonArray".equals(param))////
        {
            JSONArray  jsonArray = new JSONArray();
            JSONObject json = new JSONObject();
            json.put("totalRecords", Integer.valueOf(totalRecords));
            List stuList = stuService.findAllStudents();
            Student stu;
            for(Iterator iterator = stuList.iterator(); iterator.hasNext(); jsonArray.add(stu.toJSON()))
                stu = (Student)iterator.next();
            json.put("list",jsonArray);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(json.toString());
        } else
        if("add".equals(param))
        {
            int pageNo = 1;//Integer.parseInt(request.getParameter("pageNo"));
//            if(pageNo == totalRecords / 5)
//                pageNo++;
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String address = request.getParameter("address");
            int cid = Integer.parseInt(request.getParameter("cid"));
            Classes cls = new Classes(cid);
            Part part = request.getPart("photo");
            String photoPath = request.getSession().getServletContext().getRealPath("/");
            System.out.println(photoPath);
            String photoName = (new StringBuilder((new Integer(id)).hashCode())).append(System.currentTimeMillis()).toString();
            part.write((new StringBuilder(String.valueOf(photoPath))).append("\\").append(photoName).append(".jpg").toString());
            Student stu = new Student(id, name, age, address, photoName, cls);
            stuService.saveStudent(stu);
            response.sendRedirect((new StringBuilder("stuServlet?param=list&pageNo=")).append(pageNo).toString());
        } else
        if("addJson".equals(param))
        {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            System.out.println(id+name+request.getParameter("age"));
            int age = Integer.parseInt(request.getParameter("age"));
            String address = request.getParameter("address");
            int cid = Integer.parseInt(request.getParameter("cid"));
            /*Part part = request.getPart("photo");
            String photoPath = getServletContext().getRealPath("/");
            String photoName = (new StringBuilder((new Integer(id)).hashCode())).append(System.currentTimeMillis()).toString();
            part.write((new StringBuilder(String.valueOf(photoPath))).append("\\").append(photoName).append(".jpg").toString());*/
            Classes cls = new Classes(cid);
            Student stu = new Student(id, name, age, address ,cls);
            stuService.saveStudent(stu);
            response.getWriter().write("success");

        } else
        if("stuForm".equals(param))
        {
            List clsList = clsService.queryAllClasses();
            request.setAttribute("clsList", clsList);
            request.getRequestDispatcher("addForm.jsp").forward(request, response);
        } else
        if("classesJson".equals(param))
        {
            List clsList = clsService.queryAllClasses();
            JSONArray  jsonArray = new JSONArray();
            Classes cls;
            for(Iterator iterator = clsList.iterator(); iterator.hasNext(); jsonArray.add(cls.toJSON()))
                cls = (Classes)iterator.next();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(jsonArray.toString());
        } else
        if("modify".equals(param))//查询班级列表
        {
            int pageNo = Integer.parseInt(request.getParameter("pageNo"));
            int id = Integer.parseInt(request.getParameter("id"));
            List clsList = clsService.queryAllClasses();
            request.setAttribute("clsList", clsList);
            Student stu = stuService.findStudentById(id);
            request.setAttribute("stu", stu);
            request.getRequestDispatcher((new StringBuilder("updateForm.jsp?pageNo=")).append(pageNo).toString()).forward(request, response);
        } else
        if("update".equals(param))
        {
            int pageNo = Integer.parseInt(request.getParameter("pageNo"));
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String address = request.getParameter("address");
            int cid = Integer.parseInt(request.getParameter("cid"));
            Part part = request.getPart("photo");
            String photoPath = getServletContext().getRealPath("/");
            String photoName = (new StringBuilder((new Integer(id)).hashCode())).append(System.currentTimeMillis()).toString();
            part.write((new StringBuilder(String.valueOf(photoPath))).append("\\").append(photoName).append(".jpg").toString());
            Classes cls = new Classes(cid);
            Student stu = new Student(id, name, age, address, photoName, cls);
            stuService.updateStudent(stu);
            response.sendRedirect((new StringBuilder("stuServlet?param=list&pageNo=")).append(pageNo).toString());
        } else
        if("updateJson".equals(param))////
        {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            System.out.println(id+name+request.getParameter("age"));
            int age = Integer.parseInt(request.getParameter("age"));
            String address = request.getParameter("address");
            int cid = Integer.parseInt(request.getParameter("cid"));
            /*Part part = request.getPart("photo");
            String photoPath = getServletContext().getRealPath("/");
            String photoName = (new StringBuilder((new Integer(id)).hashCode())).append(System.currentTimeMillis()).toString();
            part.write((new StringBuilder(String.valueOf(photoPath))).append("\\").append(photoName).append(".jpg").toString());*/
            Classes cls = new Classes(cid);
            Student stu = new Student(id, name, age, address ,cls);
            stuService.updateStudent(stu);
            response.getWriter().write("success");
        } else
        if("delete".equals(param))
        {
            int pageNo = Integer.parseInt(request.getParameter("pageNo"));
            int id = Integer.parseInt(request.getParameter("id"));
            stuService.deleteStudent(id);
            totalRecords = stuService.queryTotalRecords();
            if(pageNo > (totalRecords - 1) / 5 + 1)
                pageNo--;
            List stuList = stuService.findStudentsByPageNo(pageNo);
            JSONArray  jsonArray = new JSONArray();
            Student stu;
            for(Iterator iterator = stuList.iterator(); iterator.hasNext(); jsonArray.add(stu.toJSON()))
                stu = (Student)iterator.next();

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter pw = response.getWriter();

            jsonArray.addAll(stuList);
            pw.println(jsonArray.toString());
            pw.close();
        } else
        if("delpro".equals(param))////
        {
            int id = Integer.parseInt(request.getParameter("id"));
            stuService.deleteStudent(id);
            response.getWriter().write("success");
        } else
        if("detail".equals(param))
        {
            int id = Integer.parseInt(request.getParameter("id"));
            Student stu = stuService.findStudentById(id);
            request.setAttribute("stu", stu);
            request.getRequestDispatcher("stuDetail.jsp").forward(request, response);
        } else
        if("jsonById".equals(param))////
        {
            int id = Integer.parseInt(request.getParameter("id"));
            Student stu = stuService.findStudentById(id);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(stu.toJSON());
        } else
        {
            response.sendRedirect("other.jsp");
        }
    }

    private static final long serialVersionUID = 1L;
    private StudentService stuService;
    private ClassesService clsService;
}
