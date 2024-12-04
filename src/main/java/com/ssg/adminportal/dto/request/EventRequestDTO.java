package com.ssg.adminportal.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {

    private String title;

    private MultipartFile thumbnail;

    private MultipartFile detail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void multipartThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void multipartDetail(MultipartFile detail) {
        this.detail = detail;
    }
}
