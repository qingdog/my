// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   MysqlStudentDao.java

package com.iflytek.stumanager.dao;

import com.iflytek.stumanager.po.Classes;
import com.iflytek.stumanager.po.Student;
import com.iflytek.stumanager.util.DBUtil;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.iflytek.stumanager.dao:
//            StudentDao

public class MysqlStudentDao extends StudentDao
{

    public MysqlStudentDao()
    {
        isExists = true;
    }

    public void saveStudent(Student student)
    {
        PreparedStatement pstmt;
        String sql = "insert into student (id,name,age,address,photo,classesId) values(?,?,?,?,?,?)";
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getAddress());
            pstmt.setString(5, student.getPhoto());
            pstmt.setInt(6, student.getClasses().getId());
            pstmt.executeUpdate();
            //break MISSING_BLOCK_LABEL_109;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
        //break MISSING_BLOCK_LABEL_112;
        //Exception exception;
        //exception;
        DBUtil.closeDBResources();
        //throw exception;
        DBUtil.closeDBResources();
    }

    public boolean isExists()
    {
        return isExists;
    }

    public void updateStudent(Student student)
    {
        PreparedStatement pstmt;
        String sql = "update student set name=?,age=?,address=?,photo=?,classesId=? where id=? ";
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setString(3, student.getAddress());
            pstmt.setInt(6, student.getId());
            pstmt.setString(4, student.getPhoto());
            pstmt.setInt(5, student.getClasses().getId());
            //System.out.println(student.getPhoto());
            pstmt.executeUpdate();
            //break MISSING_BLOCK_LABEL_119;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
        //break MISSING_BLOCK_LABEL_122;
        //Exception exception;
        //exception;
        DBUtil.closeDBResources();
        //throw exception;
        DBUtil.closeDBResources();
    }

    public void deleteStudent(int id)
    {
        PreparedStatement pstmt;
        String sql = "delete from student where id=?";
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            //break MISSING_BLOCK_LABEL_47;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
        //break MISSING_BLOCK_LABEL_50;
        //Exception exception;
        //exception;
        DBUtil.closeDBResources();
        //throw exception;
        //DBUtil.closeDBResources();
    }

    public Student findStudentById(int id)
    {
        Student stu;
        PreparedStatement pstmt;
        stu = null;
        String sql = "select * from student where id=?";
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
            {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                String photo = rs.getString("photo");
                int classesId = rs.getInt("classesId");
                Classes cls = new Classes(classesId);
                stu = new Student(id, name, age, address, photo, cls);
            }
            rs.close();
            //break MISSING_BLOCK_LABEL_155;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
        //break MISSING_BLOCK_LABEL_158;
        //Exception exception;
        //exception;
        DBUtil.closeDBResources();
        //throw exception;
        //DBUtil.closeDBResources();
        return stu;
    }

    public List findAllStudents()
    {
        List stuList;
        PreparedStatement pstmt;
        stuList = new ArrayList();
        String sql = "select s.id,s.name ,age,address,c.id as cid ,c.name as cname from student s join classes c on c.id=s.classesid";
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            ResultSet rs;
            Student stu;
            for(rs = pstmt.executeQuery(); rs.next(); stuList.add(stu))
            {
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
            //break MISSING_BLOCK_LABEL_175;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
        //break MISSING_BLOCK_LABEL_178;
       //Exception exception;
        //exception;
        DBUtil.closeDBResources();
        //throw exception;
        DBUtil.closeDBResources();
        return stuList;
    }

    public List findStudentsByPageNo(int pageNo)
    {
        List stuList;
        PreparedStatement pstmt;
        stuList = new ArrayList();
        String sql = (new StringBuilder("select s.id,s.name,age,address,c.id,c.name from student s join classes c on s.classesid=c.id limit ")).append((pageNo - 1) * 5).append(",5").toString();
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            ResultSet rs;
            Student stu;
            for(rs = pstmt.executeQuery(); rs.next(); stuList.add(stu))
            {
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
//            break MISSING_BLOCK_LABEL_200;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
//        break MISSING_BLOCK_LABEL_203;
        Exception exception;
//        exception;
        DBUtil.closeDBResources();
//        throw exception;
        DBUtil.closeDBResources();
        return stuList;
    }
    public List findStudentsByPageNo(int pageNo,int pageSize)
    {
        List stuList;
        PreparedStatement pstmt;
        stuList = new ArrayList();
        String sql = (new StringBuilder("select s.id,s.name,age,address,c.id,c.name from student s join classes c on s.classesid=c.id limit "))
                .append((pageNo-1)*pageSize).append(",").append(pageSize).toString();
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            ResultSet rs;
            Student stu;
            for(rs = pstmt.executeQuery(); rs.next(); stuList.add(stu))
            {
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
//            break MISSING_BLOCK_LABEL_200;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
//        break MISSING_BLOCK_LABEL_203;
        Exception exception;
//        exception;
        DBUtil.closeDBResources();
//        throw exception;
        DBUtil.closeDBResources();
        return stuList;
    }

    public int queryTotalRecords()
    {
        int totalRecores;
        PreparedStatement pstmt;
        totalRecores = 0;
        String sql = "select count(*) as total from student";
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                totalRecores = rs.getInt("total");
            rs.close();
//            break MISSING_BLOCK_LABEL_69;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
//        break MISSING_BLOCK_LABEL_72;
//        Exception exception;
//        exception;
        DBUtil.closeDBResources();
//        throw exception;
        DBUtil.closeDBResources();
        return totalRecores;
    }

    boolean isExists;
}
