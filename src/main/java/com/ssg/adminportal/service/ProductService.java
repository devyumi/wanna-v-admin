package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.dto.request.ProductListRequestDTO;
import com.ssg.adminportal.dto.request.ProductRequestDTO;
import com.ssg.adminportal.dto.response.ProductResponseDTO;
import java.util.List;

public interface ProductService {

    List<Product> getProductList();

    ProductResponseDTO filterProductList(ProductListRequestDTO requestDTO);

    Product getProduct(Long productId);

    void createProduct(Long adminId, ProductRequestDTO requestDTO);

    void modifyProduct(Long productId, ProductRequestDTO requestDTO);
}