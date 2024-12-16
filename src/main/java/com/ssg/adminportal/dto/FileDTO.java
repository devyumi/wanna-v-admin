package com.ssg.adminportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    private String originalFileName;    // test.png
    private String uploadFileName;      // UUID.png
    private String uploadFilePath;      // Object Storage에 설정한 경로
    private String uploadFileUrl;       // file url
}
