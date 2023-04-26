package com.example.bootwebplus.book.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootwebplus.book.model.po.TblBook;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-04-24
 */
public interface TblBookService extends IService<TblBook> {

    Page<TblBook> getPage(int pageNo, int pageSize);

    Page<TblBook> getPage(int pageNo, int pageSize, TblBook tblBook);

}
