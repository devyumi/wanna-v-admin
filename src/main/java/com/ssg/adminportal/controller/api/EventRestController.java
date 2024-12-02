package com.ssg.adminportal.controller.api;

import com.ssg.adminportal.dto.request.EventRequestDTO;
import com.ssg.adminportal.dto.request.ProductRequestDTO;
import com.ssg.adminportal.dto.request.UserListRequestDTO;
import com.ssg.adminportal.dto.response.EventListResponseDTO;
import com.ssg.adminportal.dto.response.UserListResponseDTO;
import com.ssg.adminportal.service.EventService;
import com.ssg.adminportal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventRestController {

    private final EventService eventService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> userList(
        @RequestParam int page,
        @RequestParam int size) {

        UserListRequestDTO requestDTO = UserListRequestDTO.builder()
            .page(page)
            .size(size)
            .build();

        EventListResponseDTO responseDTO = eventService.getPageAll(requestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", responseDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> createEvent(
            @RequestPart("event") @Validated EventRequestDTO requestDTO,
            @RequestPart("thumbnail") MultipartFile thumbnail,
            @RequestPart("detail") MultipartFile detail) {

        if (thumbnail != null)
            requestDTO.multipartThumbnail(thumbnail);

        if (detail != null)
            requestDTO.multipartDetail(detail);


        Long adminId = 1L;

        eventService.createEvent(adminId, requestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
