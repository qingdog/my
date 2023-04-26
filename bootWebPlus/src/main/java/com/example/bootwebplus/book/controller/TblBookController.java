package com.example.bootwebplus.book.controller;

import com.example.bootwebplus.book.model.po.TblBook;
import com.example.bootwebplus.book.service.TblBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("tblBook")
@Api(value = "TblBook Api", tags = "书籍")
public class TblBookController {

    @Autowired
    private TblBookService tblBookService;

    @ApiOperation("书本列表查询api")
    @GetMapping
    public List<TblBook> queryTreeNodes() {
        return tblBookService.list();
    }

    @ApiOperation("书本通过id查询api")
    @GetMapping("/{id}")
    public Object getById(@PathVariable Integer id) {
        return tblBookService.getById(id);
    }

    @ApiOperation("书本分页查询api")
    @GetMapping("/{pageSize}/{pageNo}")
    public Object getPage(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return tblBookService.getPage(pageNo, pageSize);
    }

    @ApiOperation("书本分页POST有条件查询api")
    @PostMapping("/{pageSize}/{pageNo}")
    public Object getPage(@PathVariable Integer pageNo, @PathVariable Integer pageSize, @RequestBody TblBook tblBook) {
        return tblBookService.getPage(pageNo, pageSize, tblBook);
    }

    @ApiOperation("书本添加api")
    @PostMapping()
    public Object save(@RequestBody TblBook tblBook) {
        return tblBookService.save(tblBook);
    }

    @ApiOperation("书本删除api")
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Integer id) {
        return tblBookService.removeById(id);
    }

    @ApiOperation("书本更新api")
    @PutMapping
    public Object update(@RequestBody TblBook tblBook) {
        return tblBookService.updateById(tblBook);
    }
}
