package com.itheima.service;

import com.itheima.dao.GoodsMapper;
import com.itheima.pojo.Goods;
import com.itheima.pojo.GoodsDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service

public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescService goodsDescService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(){

        //保存商品
        Goods goods = new Goods();
        goods.setTitle("小米13");
        goods.setImg("https://cdn.cnbj1.fds.api.mi-img.com/product-images/xiaomi-13kb7buy/5.png?x-fds-process=image/resize,q_90,f_webp");
        goods.setBrandId("小米");
        goods.setPrice(3999d);
        goodsMapper.insert(goods);


        //保存商品详情
        GoodsDesc desc = new GoodsDesc();
        desc.setGoodsId(goods.getId());
        desc.setSaleService("质保一年");
        desc.setIntroduction("原汁原味徕卡影像质感再飞跃");
        desc.setPackageList("纸盒子");
        goodsDescService.save(desc);

        //异常
        int a = 1/0;
    }
}
