package com.ssg.adminportal.repository;

import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.dto.request.ProductListRequestDTO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCustomRepository {

    List<Product> findAll(ProductListRequestDTO requestDTO);

    Integer count(ProductListRequestDTO requestDTO);
}
