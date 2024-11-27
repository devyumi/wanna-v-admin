package com.ssg.adminportal.service;

import com.ssg.adminportal.dto.FileDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String getUuidFileName(String fileName);
    List<FileDTO> uploadFiles(List<MultipartFile> multipartFiles, String filePath);
}
