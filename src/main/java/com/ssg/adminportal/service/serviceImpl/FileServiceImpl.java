package com.ssg.adminportal.service.serviceImpl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.adminportal.config.NcpConfig;
import com.ssg.adminportal.dto.FileDTO;
import com.ssg.adminportal.service.FileService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AmazonS3Client amazonS3Client;
    private final NcpConfig ncpConfig;

    public String getUuidFileName(String fileName) {
        //파일 확장자 (ex. png, jpg ...)
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return UUID.randomUUID() + "." + ext;
    }

    public FileDTO uploadFile(MultipartFile multipartFile, String filePath) {
        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);
        String uploadFileUrl = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            String keyName = filePath + "/" + uploadFileName;

            amazonS3Client.putObject(
                new PutObjectRequest(ncpConfig.getBucketName(), keyName, inputStream,
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            uploadFileUrl = amazonS3Client.getUrl(ncpConfig.getBucketName(), keyName).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FileDTO.builder()
                .originalFileName(originalFileName)
                .uploadFileName(uploadFileName)
                .uploadFilePath(filePath)
                .uploadFileUrl(uploadFileUrl)
                .build();
    }

    public List<FileDTO> uploadFiles(List<MultipartFile> multipartFiles, String filePath) {
        List<FileDTO> s3files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                String keyName = filePath + "/" + uploadFileName;

                amazonS3Client.putObject(
                    new PutObjectRequest(ncpConfig.getBucketName(), keyName, inputStream,
                        objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                uploadFileUrl = amazonS3Client.getUrl(ncpConfig.getBucketName(), keyName)
                    .toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            s3files.add(
                FileDTO.builder()
                    .originalFileName(originalFileName)
                    .uploadFileName(uploadFileName)
                    .uploadFilePath(filePath)
                    .uploadFileUrl(uploadFileUrl)
                    .build());

        }
        return s3files;
    }

    /**
     * DB에 저장되는 imgUrl 생성 - ["url1", "url2", "url3"] 형식
     *
     * @param fileDTOS
     * @return
     */
    public List<String> convertImageUrlsToJson(List<FileDTO> fileDTOS) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // FileDTO 객체에서 uploadFileUrl만 추출하여 리스트로 저장
            return fileDTOS.stream()
                .map(FileDTO::getUploadFileUrl)
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * DB에 저장된 JSON 형식을 List<String> 으로 파싱
     *
     * @param json
     * @return
     */
    public List<String> parseImageUrlsFromJson(String json) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
    } catch (Exception e) {
        e.printStackTrace();
        return List.of();
    }
}

}
