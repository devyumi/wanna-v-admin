package com.ssg.adminportal.controller.api;

import com.ssg.adminportal.dto.response.ProductResponseDTO;
import com.ssg.adminportal.service.ProductService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getProductList() {
        List<ProductResponseDTO> products = productService.getProductList();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", products);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
