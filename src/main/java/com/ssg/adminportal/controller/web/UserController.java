package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.domain.User;
import com.ssg.adminportal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public String userList(Model model){
        List<User> userList = userService.getAll();
        model.addAttribute("userList", userList);
        return "/user/users";
    }

    @GetMapping("/{userId}")
    public String user(@PathVariable Long userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "user/user";
    }
}