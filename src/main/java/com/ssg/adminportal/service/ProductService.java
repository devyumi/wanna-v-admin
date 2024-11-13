package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.dto.response.ProductResponseDTO;
import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> getProductList();

    Product getProduct(Long productId);
}