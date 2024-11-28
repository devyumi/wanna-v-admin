package com.ssg.adminportal.service;

import com.ssg.adminportal.dto.FileDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String getUuidFileName(String fileName);
    FileDTO uploadFile(MultipartFile multipartFile, String filePath);
    List<FileDTO> uploadFiles(List<MultipartFile> multipartFiles, String filePath);
    List<String> convertImageUrlsToJson(List<FileDTO> fileDTOS);
    List<String> parseImageUrlsFromJson(String json);
}
