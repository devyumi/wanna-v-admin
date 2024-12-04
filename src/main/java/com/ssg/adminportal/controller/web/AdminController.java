package com.ssg.adminportal.controller.web;

import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.domain.UserGradeLog;
import com.ssg.adminportal.service.AdminService;
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
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @GetMapping()
    public String adminList(){
        return "admin/admins";
    }

    @GetMapping("/{adminId}")
    public String admin(@PathVariable Long adminId, Model model) {
        Admin admin = adminService.getAdmin(adminId);
        model.addAttribute("admin", admin);
        return "admin/admin";
    }
}