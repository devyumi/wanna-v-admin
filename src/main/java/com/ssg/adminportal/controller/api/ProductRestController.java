package com.ssg.adminportal.controller.api;

import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.dto.request.ProductRequestDTO;
import com.ssg.adminportal.dto.response.ProductResponseDTO;
import com.ssg.adminportal.service.ProductService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;
    final Long adminId = 1L; // Security 적용 후 삭제 예정

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getProductList() {
        List<ProductResponseDTO> products = productService.getProductList();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", products);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> createProduct(
        @RequestPart("product") @Validated ProductRequestDTO requestDTO,
        @RequestPart("image") MultipartFile image,            // 파일을 @RequestPart로 받음
        @RequestPart("description") List<MultipartFile> description) { // 여러 파일을 받는 경우 List 사용

        if (image != null) {
            requestDTO.multipartImage(image);
        }

        if (description != null && !description.isEmpty()) {
            requestDTO.multipartDescription(description);
        }

        productService.createProduct(adminId, requestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "/{productId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> modifyProduct(@PathVariable Long productId,
        @RequestPart("product") ProductRequestDTO requestDTO,
        @RequestPart("updateImage") MultipartFile image,
        @RequestPart("updateDescription") List<MultipartFile> description) {

        if (image != null) {
            requestDTO.multipartImage(image);
        }

        if (description != null && !description.isEmpty()) {
            requestDTO.multipartDescription(description);
        }

        productService.modifyProduct(productId, requestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
