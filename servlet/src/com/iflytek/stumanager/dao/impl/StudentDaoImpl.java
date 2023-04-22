package com.iflytek.stumanager.dao.impl;

import com.iflytek.stumanager.dao.StudentDao;
import com.iflytek.stumanager.po.Classes;
import com.iflytek.stumanager.po.Student;
import com.iflytek.stumanager.util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    boolean isExists;

    public StudentDaoImpl() {
        isExists = true;
    }

    public int queryTotalRecords() {
        int totalRecores = 0;
        String sql = "select count(*) as total from student";
        PreparedStatement pstmt = DBUtil.getPreparedStatement(sql);
        try {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                totalRecores = rs.getInt("total");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBResources();
        }
        return totalRecores;
    }

    public void saveStudent(Student student) {
        PreparedStatement pstmt;
        String sql = "insert into student (id,name,age,address,photo,classesId) values(?,?,?,?,?,?)";
        pstmt = DBUtil.getPreparedStatement(sql);
        try {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getAddress());
            pstmt.setString(5, student.getPhoto());
            pstmt.setInt(6, student.getClasses().getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBResources();
        }
    }

    public boolean isExists() {
        return isExists;
    }

    public void updateStudent(Student student) {
        PreparedStatement pstmt;
        String sql = "update student set name=?,age=?,address=?,photo=?,classesId=? where id=? ";
        pstmt = DBUtil.getPreparedStatement(sql);
        try {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setString(3, student.getAddress());
            pstmt.setInt(6, student.getId());
            pstmt.setString(4, student.getPhoto());
            pstmt.setInt(5, student.getClasses().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBResources();
        }
    }

    public void deleteStudent(int id) {
        PreparedStatement pstmt;
        String sql = "delete from student where id=?";
        pstmt = DBUtil.getPreparedStatement(sql);
        try {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBResources();
        }
    }

    public Student findStudentById(int id) {
        Student stu;
        PreparedStatement pstmt;
        stu = null;
        String sql = "select * from student where id=?";
        pstmt = DBUtil.getPreparedStatement(sql);
        try {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                String photo = rs.getString("photo");
                int classesId = rs.getInt("classesId");
                Classes cls = new Classes(classesId);
                stu = new Student(id, name, age, address, photo, cls);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBResources();
        }
        return stu;
    }

    public List findAllStudents() {
        List stuList;
        PreparedStatement pstmt;
        stuList = new ArrayList();
        String sql = "select s.id,s.name ,age,address,c.id as cid ,c.name as cname from student s join classes c on c.id=classesid";
        pstmt = DBUtil.getPreparedStatement(sql);
        try {
            ResultSet rs;
            Student stu;
            for (rs = pstmt.executeQuery(); rs.next(); stuList.add(stu)) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                int cid = rs.getInt("cid");
                String cname = rs.getString("cname");
                Classes cls = new Classes(cid, cname);
                stu = new Student(id, name, age, address, cls);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBResources();
        }
        return stuList;
    }

    public List findStudentsByPageNo(int pageNo, int pageSize) {
        List stuList;
        PreparedStatement pstmt;
        stuList = new ArrayList();
        String sql = "select s.id,s.name,age,address,c.id,c.name from student s join classes c on classesid=c.id " +
                "limit " + (pageNo - 1) * pageSize + "," + pageSize;
        return getList(stuList, sql);
    }

    private List getList(List stuList, String sql) {
        PreparedStatement pstmt;
        pstmt = DBUtil.getPreparedStatement(sql);
        try {
            ResultSet rs;
            Student stu;
            for (rs = pstmt.executeQuery(); rs.next(); stuList.add(stu)) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                int cid = rs.getInt("c.id");
                String cname = rs.getString("c.name");
                Classes cls = new Classes(cid, cname);
                stu = new Student(id, name, age, address, cls);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBResources();
        }
        return stuList;
    }

}
