package com.example.book.controller;

import com.example.book.domain.User;
import com.example.book.domain.UserDetails;
import com.example.book.service.UserService;
import com.example.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String userForm(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        model.addAttribute("users", userService.list());

        List<User> list = userService.list();
        for (User user : list) {
            UserDetails details = user.getDetails();
            if (details != null) {
                System.out.println(details);
                System.out.println(new JsonUtil().beanToJson(user));

                HashSet<Class<?>> set = new HashSet<>();
                set.add(User.class);

                System.out.println(set.contains(details.getUser().getClass()));
                System.out.println(details.getUser().getClass());
            }
        }
        return "editUsers";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object userForm() {
        List<User> list = userService.list();
        for (User user : list) {
            UserDetails details = user.getDetails();
            if (details != null) {
                return new JsonUtil().setExcludeClassFieldNames(details.getClass(), Collections.singletonList("class")).beanToJson(user);
            }
        }
        return list;
    }

    @ModelAttribute("user")
    public User formBackingObject() {
        return new User();
    }

    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult result, Model model) throws UnsupportedEncodingException {
        // 不要给user设置id，因为使用了自增主键
        if (result.hasErrors()) {
            model.addAttribute("users", userService.list());
            return "editUsers";
        }
        String name = user.getName();
        System.out.println(name);
        System.out.println(new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));


        userService.save(user);
        return "redirect:/";
    }
}