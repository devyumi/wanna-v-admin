package com.ssg.adminportal.controller.api;

import com.ssg.adminportal.dto.request.AdminListRequestDTO;
import com.ssg.adminportal.dto.request.EventRequestDTO;
import com.ssg.adminportal.dto.request.PageListRequestDTO;
import com.ssg.adminportal.dto.response.AdminListResponseDTO;
import com.ssg.adminportal.dto.response.EventListResponseDTO;
import com.ssg.adminportal.service.AdminService;
import com.ssg.adminportal.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> adminList(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(defaultValue = "") String role,
        @RequestParam(defaultValue = "") String name) {

        AdminListRequestDTO requestDTO = AdminListRequestDTO.builder()
            .page(page)
            .size(size)
            .role(role)
            .name(name)
            .build();

        AdminListResponseDTO responseDTO = adminService.getAll(requestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", responseDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
