package com.iflytek.stumanager.servlet;

import com.iflytek.stumanager.business.ClassesService;
import com.iflytek.stumanager.business.StudentService;
import com.iflytek.stumanager.po.Classes;
import com.iflytek.stumanager.po.Student;
import com.iflytek.stumanager.util.PageModel;
import com.mysql.jdbc.StringUtils;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

// 使用编码方式为multipart/form-data方式提交
@MultipartConfig
public class StudentServlet extends HttpServlet {
    private final StudentService stuService;
    private final ClassesService clsService;

    public StudentServlet() {
        stuService = new StudentService();
        clsService = new ClassesService();
    }

    PageModel getPageModel(int pageNo) {
        // 总条数
        int totalRecords = stuService.queryTotalRecords();
        int pageSize = 5;
        int totalPages = (totalRecords - 1) / pageSize + 1;

        pageNo = pageNo <= 0 ? 1 : pageNo;
        pageNo = Math.min(pageNo, totalPages);

        return new PageModel(pageNo, totalPages, pageSize, totalRecords);
    }

    void setHeader(HttpServletResponse response) {
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
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        this.setHeader(response);
        String param = request.getParameter("param");
        System.out.println(this.getClass() + " param: " + param);

        if (param == null) {
            String id = request.getParameter("id");
            // 单个查询
            if (!StringUtils.isNullOrEmpty(id)) {
                Student student = stuService.findStudentById(Integer.parseInt(id));
                request.setAttribute("student", student);
                request.getRequestDispatcher("stuDetail.jsp").forward(request, response);
            } else {
                // 分页查询
                String pageNo = request.getParameter("pageNo");
                PageModel pageModel = this.getPageModel(Integer.parseInt(StringUtils.isNullOrEmpty(pageNo) ? "1" : pageNo));
                List<?> studentList = stuService.findStudentsByPageNo(pageModel.getPageNo(), pageModel.getPageSize());

                pageModel.setList(studentList);
                request.setAttribute("pageModel", pageModel);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else if (param.equals("add")) {
            this.add(request, response);
        } else if (param.equals("save")) {
            this.save(request, response);
        }else if (param.equals("delete")) {
            this.delete(request, response);
        } else if (param.equals("modify")) {
            this.modify(request, response);
        }  else if (param.equals("update")) {
            this.update(request, response);
        }  else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int id = Integer.parseInt(request.getParameter("id"));

        List clsList = clsService.queryAllClasses();
        request.setAttribute("clsList", clsList);
        Student stu = stuService.findStudentById(id);
        request.setAttribute("stu", stu);
        request.getRequestDispatcher("updateForm.jsp?pageNo=" + pageNo).forward(request, response);
    }

    void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");
        int cid = Integer.parseInt(request.getParameter("cid"));

        Part part = request.getPart("photo");
        String photoPath = getServletContext().getRealPath("/photos");
        String photoName = String.valueOf(System.currentTimeMillis());
        part.write(String.valueOf(photoPath) + "\\" + photoName + ".jpg");
        Classes cls = new Classes(cid);
        Student stu = new Student(id, name, age, address, photoName, cls);
        stuService.updateStudent(stu);
        response.sendRedirect("stuServlet?pageNo=" + pageNo);
    }

    void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        stuService.deleteStudent(id);

        int pageNo = Integer.parseInt(StringUtils.isNullOrEmpty(
                request.getParameter("pageNo")) ? "1" : request.getParameter("pageNo"));
        PageModel pageModel = this.getPageModel(pageNo);

        if (pageNo > (pageModel.getTotalRecords() - 1) / 5 + 1)
            pageNo--;
        List<?> stuList = stuService.findStudentsByPageNo(pageNo, pageModel.getPageSize());
        JSONArray jsonArray = null;

        // commons-lang.jar
        jsonArray= new JSONArray();
//        Student stu;
//        for (Iterator<?> iterator = stuList.iterator(); iterator.hasNext(); jsonArray.add(JSONObject.fromObject(stu))) {
//            stu = (Student) iterator.next();
//        }
        jsonArray.addAll(stuList);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(jsonArray);
    }

    void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<?> allClasses = clsService.queryAllClasses();
        request.setAttribute("list", allClasses);
        request.getRequestDispatcher("addForm.jsp").forward(request, response);
    }

    void save(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int pageNo = Integer.parseInt(StringUtils.isNullOrEmpty(
                request.getParameter("pageNo")) ? "1" : request.getParameter("pageNo"));
        PageModel pageModel = this.getPageModel(pageNo);
        if (pageModel.getTotalPages() == pageModel.getTotalRecords() / 5) {
            pageModel.setPageNo(pageModel.getPageNo() + 1);
        }

        String param = request.getParameter("id");
        int id = Integer.parseInt(param);
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");
        int cid = Integer.parseInt(request.getParameter("cid"));
        Classes cls = new Classes(cid);

        String photoPath = request.getSession().getServletContext().getRealPath("/photos");
        if (!new File(photoPath).exists()) {
            boolean mkdir = new File(photoPath).mkdir();
            if (!mkdir) {
                throw new RuntimeException(photoPath + "照片目录创建失败！");
            }
        }
        String photoName = String.valueOf(System.currentTimeMillis());
        Part part = request.getPart("photo");
        part.write(photoPath + "\\" + photoName + ".jpg");
        System.out.println("照片路径：" + photoPath + photoName + ".jpg");
        Student stu = new Student(id, name, age, address, photoName, cls);

        stuService.saveStudent(stu);
        response.sendRedirect("stuServlet?pageNo=" + pageModel.getPageNo());
    }

}
