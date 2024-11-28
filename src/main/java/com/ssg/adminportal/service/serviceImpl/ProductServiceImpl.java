package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.common.ErrorCode;
import com.ssg.adminportal.config.NcpConfig;
import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.dto.FileDTO;
import com.ssg.adminportal.dto.request.ProductRequestDTO;
import com.ssg.adminportal.dto.response.ProductResponseDTO;
import com.ssg.adminportal.exception.CustomException;
import com.ssg.adminportal.repository.AdminRepository;
import com.ssg.adminportal.repository.ProductRepository;
import com.ssg.adminportal.service.FileService;
import com.ssg.adminportal.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final FileService fileService;
    private final NcpConfig ncpConfig;


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
     *
     * @param productId → 상품 ID
     */
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new CustomException(ErrorCode.NON_EXISTENT_ID)
        );
    }

    /**
     * 상품 등록
     *
     * @param requestDTO
     */
    @Transactional
    public void createProduct(Long adminId, ProductRequestDTO requestDTO) {
        String imageUrl = null;
        String descriptionUrl = null;
        if (!requestDTO.getImage().isEmpty() && !requestDTO.getDescription().get(0).isEmpty()) {
            FileDTO imageFile = fileService.uploadFile(requestDTO.getImage(),
                ncpConfig.getProductPath());
            imageUrl = imageFile.getUploadFileUrl();
            List<FileDTO> descriptionFiles = fileService.uploadFiles(requestDTO.getDescription(),
                ncpConfig.getProductPath());
            descriptionUrl = fileService.convertImageUrlsToJson(descriptionFiles);
        }

        Admin admin = adminRepository.findById(adminId)
            .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

        productRepository.save(Product.builder()
            .name(requestDTO.getName())
            .image(imageUrl)
            .costPrice(requestDTO.getCostPrice())
            .sellingPrice(requestDTO.getSellingPrice())
            .discountRate(requestDTO.getDiscountRate())
            .finalPrice(requestDTO.getFinalPrice())
            .category(requestDTO.getCategory())
            .stock(requestDTO.getStock())
            .description(descriptionUrl)
            .isActive(requestDTO.getIsActive())
            .createdById(admin)
            .createdAt(requestDTO.getCreatedAt())
            .build());
    }

    /**
     * 상품 수정
     *
     * @param productId  - 수정할 상품 ID
     * @param requestDTO
     */
    @Transactional
    public void modifyProduct(Long productId, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new CustomException(ErrorCode.NON_EXISTENT_ID)
        );

        product.update(requestDTO);
    }

}
