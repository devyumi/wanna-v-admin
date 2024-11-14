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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> createProduct(@RequestBody ProductRequestDTO requestDTO) {
        productService.createProduct(requestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Map<String, String>> modifyProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO requestDTO) {
        productService.modifyProduct(productId, requestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
