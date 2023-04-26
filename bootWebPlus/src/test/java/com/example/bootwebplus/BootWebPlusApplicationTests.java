package com.example.bootwebplus;

import com.example.bootwebplus.book.service.TblBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootWebPlusApplicationTests {

    @Autowired
    TblBookService tblBookService;

    @Test
    void contextLoads() {
        System.out.println(tblBookService.getById(1));
    }

}
