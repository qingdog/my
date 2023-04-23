package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.config.common.Code;
import com.itheima.config.common.Result;
import com.itheima.domain.Book;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Result save(@RequestBody Book book) {
        boolean flag = bookService.save(book);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }

    @PutMapping
    public Result update(@RequestBody Book book) {
        boolean flag = bookService.update(book);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = bookService.delete(id);
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        Integer code = book != null ? Code.GET_OK : Code.GET_ERR;
        String msg = book != null ? "" : "数据查询失败，请重试！";
        return new Result(code,book,msg);
    }

    @GetMapping("/{pageSize}/{pageNum}")
    public Result getAll(@PathVariable Integer pageSize, @PathVariable Integer pageNum) {
//        List bookList = bookService.getAll();
        PageInfo<Book> pageInfo = bookService.getPageInfo(pageSize, pageNum);
        Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
        String msg = pageInfo != null ? "" : "数据查询失败，请重试！";

        return new Result(code, pageInfo, msg);
    }
}