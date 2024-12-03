package com.ssg.adminportal.controller.api;

import com.ssg.adminportal.dto.request.PageListRequestDTO;
import com.ssg.adminportal.dto.request.UserListRequestDTO;
import com.ssg.adminportal.dto.response.UserListResponseDTO;
import com.ssg.adminportal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> userList(
        @RequestParam int page, @RequestParam int size, @RequestParam String username) {

        UserListRequestDTO requestDTO = UserListRequestDTO.builder()
            .page(page)
            .size(size)
            .username(username)
            .build();

        UserListResponseDTO responseDTO = userService.getPageAll(requestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", responseDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
