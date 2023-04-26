package com.example.bootwebplus.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootwebplus.book.mapper.TblBookMapper;
import com.example.bootwebplus.book.model.po.TblBook;
import com.example.bootwebplus.book.service.TblBookService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class TblBookServiceImpl extends ServiceImpl<TblBookMapper, TblBook> implements TblBookService {
    @Autowired
    TblBookMapper tblBookMapper;

    public Page<TblBook> getPage(int pageNo, int pageSize) {
        return tblBookMapper.selectPage(new Page<>(pageNo, pageSize), new LambdaQueryWrapper<>());
    }

    // 无法从 static 上下文引用非 static 方法
    public Page<TblBook> getPage(int pageNo, int pageSize, TblBook tblBook) {
//        Page<TblBook> page = tblBookMapper.selectPage(new Page<TblBook>(pageNo, pageSize), new LambdaQueryWrapper<TblBook>()
//                .eq(tblBook.getId() != null, new SFunction<TblBook, Integer>() {
//                    @Override
//                    public Integer apply(TblBook tblBook) {
//                        return tblBook.getId();
//                    }
//                }, id));
        Page<TblBook> page = tblBookMapper.selectPage(new Page<>(pageNo, pageSize), new LambdaQueryWrapper<TblBook>()
                .eq(StringUtils.checkValNotNull(tblBook.getId()), TblBook::getId, tblBook.getId())
                .eq(StringUtils.isNotBlank(tblBook.getName()), TblBook::getName, tblBook.getName())
                .eq(StringUtils.isNotBlank(tblBook.getType()), TblBook::getType, tblBook.getType())
                .eq(StringUtils.isNotBlank(tblBook.getDescription()), TblBook::getDescription, tblBook.getDescription()));
        return page;
    }
}
