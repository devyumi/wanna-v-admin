package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.dto.response.ProductResponseDTO;
import com.ssg.adminportal.repository.ProductRepository;
import com.ssg.adminportal.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductList() {
        List<Product> products = productRepository.findAll(
            Sort.by(Direction.DESC, "id"));

        return products.stream()
            .map(product -> new ProductResponseDTO(product.getId(), product.getName(),
                product.getFinalPrice(), product.getCategory(), product.getStock(),
                product.getIsActive(), product.getCreatedAt()))
            .collect(Collectors.toList());
    }

    /**
     * 상품 상세 조회
     * @param productId → 상품 ID
     */
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid ID value: " + productId));
    }

}
