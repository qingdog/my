package com.itheima.service;

import com.itheima.dao.GoodsDescMapper;
import com.itheima.pojo.GoodsDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsDescService {

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(GoodsDesc goodsDesc){
        goodsDescMapper.insert(goodsDesc);
    }
}
