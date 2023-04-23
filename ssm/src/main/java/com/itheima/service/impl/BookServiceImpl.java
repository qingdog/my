package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.config.common.Code;
import com.itheima.dao.BookDao;
import com.itheima.domain.Book;
import com.itheima.config.exception.BusinessException;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    public boolean save(Book book) {
        return bookDao.save(book) > 0;
    }

    public boolean update(Book book) {
        return bookDao.update(book) > 0;
    }

    public boolean delete(Integer id) {
        return bookDao.delete(id) > 0;
    }

    public Book getById(Integer id) {
        if(id == 1){
            throw new BusinessException(Code.BUSINESS_ERR,"请不要使用你的技术挑战我的耐性!");
        }
//        //将可能出现的异常进行包装，转换成自定义异常
//        try{
//            int i = 1/0;
//        }catch (Exception e){
//            throw new SystemException(Code.SYSTEM_TIMEOUT_ERR,"服务器访问超时，请重试!",e);
//        }
        return bookDao.getById(id);
    }

    public List getAll() {
        PageInfo<Book> objectPageInfo = PageHelper.startPage(1, 2).doSelectPageInfo(() -> bookDao.getAll());
        List<Book> userEntities = bookDao.getAll();
        PageInfo<Book> userEntityPageInfo = new PageInfo<>(userEntities);

        Page<Book> page = PageHelper.startPage(1, 3).doSelectPage(()-> bookDao.getAll());
        List<Book> list = page.getResult();

        PageInfo<Book> pageInfo = new PageInfo<>(list);
        System.out.println("当前页："+pageInfo.getPageNum());
        System.out.println("总数据量："+pageInfo.getTotal());
        System.out.println("总页码：" +pageInfo.getPages());
        System.out.println("页面大小：" +pageInfo.getPageSize());
        System.out.println("最开头那一页："+ pageInfo.getNavigateFirstPage());
        System.out.println("每一页的页号");

        for( int pageNum : pageInfo.getNavigatepageNums()){
            System.out.println(pageNum);
        }

//        return bookDao.getAll();
        return page;
    }
    @Override
    public PageInfo<Book> getPageInfo(Integer pageSize, Integer pageNum) {
        try (Page<Object> page = PageHelper.startPage(pageNum, pageSize)) {
            return page.doSelectPageInfo(() -> bookDao.getAll());
        }
    }

}
