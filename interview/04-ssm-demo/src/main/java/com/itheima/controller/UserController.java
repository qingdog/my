package com.itheima.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.annotation.Log;
import com.itheima.exception.BusinessException;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/getById/{id}")
    @ResponseBody
    public User getById(@PathVariable("id") Integer id) {
        //手动抛出异常
        if (id == 1) {
            throw new BusinessException(ResponseEntity.status(500).body("用户id为1，没有数据"));
        }
        //运行时异常
        if (id == 2) {
            int a = 1 / 0;
        }
        return userService.getById(id);
    }
    @GetMapping("/getByPage")
    public Page getByPage(@RequestParam("page") int page,
                          @RequestParam("size") int size,
                          @RequestParam("title") int name) {

        return userService.getByPage(page, size, name);

    }
    @GetMapping("/checkUserLoginInfo")
    public User checkToken(@RequestHeader(value = "token") String token) {
        return userService.checkToken(token);
    }
    @PostMapping("/save")
    public void save(@RequestBody User user) {
        userService.save(user);
    }

    @PutMapping("/update")
    public void update(User user) {
        userService.update(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }
}
