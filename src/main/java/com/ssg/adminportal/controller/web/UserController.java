package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.domain.UserGradeLog;
import com.ssg.adminportal.service.UserGradeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserGradeLogService userGradeLogService;

    @GetMapping()
    public String userList(){
        return "user/users";
    }

    @GetMapping("/{userId}")
    public String user(@PathVariable Long userId, Model model) {
        UserGradeLog user = userGradeLogService.getUser(userId);
        model.addAttribute("user", user);
        return "user/user";
    }
}