package com.itheima.service;

import com.itheima.annotation.Log;
import com.itheima.dao.AccountMapper;
import com.itheima.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountDao;

    public Account getById(Integer id) {
        return accountDao.selectById(id);
    }

    /**
     * 转账
     *
     * @param from  减钱的用户id
     * @param to    加强的用户id
     * @param money 金额
     */
    @Transactional(rollbackFor = Exception.class)
    void update(Integer from, Integer to, Double money) throws FileNotFoundException {
        //转账的用户不能为空
        Account fromAccount = accountDao.selectById(from);
        //判断用户的钱是否够转账
        if (fromAccount.getMoney() - money >= 0) {
            fromAccount.setMoney(fromAccount.getMoney() - money);
            accountDao.updateById(fromAccount);

            new FileInputStream("abc");


            //被转账的用户
            Account toAccount = accountDao.selectById(to);
            toAccount.setMoney(toAccount.getMoney() + money);
            accountDao.updateById(toAccount);
        }


    }
}
