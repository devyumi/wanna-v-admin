package com.ssg.adminportal.service.serviceImpl;

import com.ssg.adminportal.common.ErrorCode;
import com.ssg.adminportal.config.NcpConfig;
import com.ssg.adminportal.domain.Admin;
import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.dto.request.ProductListRequestDTO;
import com.ssg.adminportal.dto.FileDTO;
import com.ssg.adminportal.dto.request.ProductRequestDTO;
import com.ssg.adminportal.dto.response.ProductResponseDTO;
import com.ssg.adminportal.exception.CustomException;
import com.ssg.adminportal.repository.AdminRepository;
import com.ssg.adminportal.repository.ProductRepository;
import com.ssg.adminportal.service.FileService;
import com.ssg.adminportal.service.ProductService;
import java.util.List;
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
    public List<Product> getProductList() {
        return productRepository.findAll(
            Sort.by(Direction.DESC, "id"));
    }

    /**
     * 상품 조건 필터링 조회
     */
    @Transactional(readOnly = true)
    public ProductResponseDTO filterProductList(ProductListRequestDTO requestDTO) {
        return ProductResponseDTO.builder()
            .requestDTO(requestDTO)
            .products(productRepository.findAll(requestDTO))
            .total(productRepository.count(requestDTO))
            .build();
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
        List<String> descriptionUrls = null;
        if (!requestDTO.getImage().isEmpty() && !requestDTO.getDescription().get(0).isEmpty()) {
            FileDTO imageFile = fileService.uploadFile(requestDTO.getImage(),
                ncpConfig.getProductPath());
            imageUrl = imageFile.getUploadFileUrl();

            // 설명 파일 업로드 및 List<String>으로 변환
        List<FileDTO> descriptionFiles = fileService.uploadFiles(requestDTO.getDescription(),
            ncpConfig.getProductPath());
        descriptionUrls = fileService.convertImageUrlsToJson(descriptionFiles);
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
            .description(descriptionUrls)
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

        String updateImageUrl = null;
        List<String> updateDescriptionUrl = null;

        if (!requestDTO.getImage().isEmpty()) {
            FileDTO imageFile = fileService.uploadFile(requestDTO.getImage(),
                ncpConfig.getProductPath());
            updateImageUrl = imageFile.getUploadFileUrl();
        }

        if (!requestDTO.getDescription().get(0).isEmpty()) {
            List<FileDTO> descriptionFiles = fileService.uploadFiles(requestDTO.getDescription(),
                ncpConfig.getProductPath());
            updateDescriptionUrl = fileService.convertImageUrlsToJson(descriptionFiles);
        }

        Product product = productRepository.findById(productId).orElseThrow(
            () -> new CustomException(ErrorCode.NON_EXISTENT_ID)
        );

        product.update(requestDTO, updateImageUrl, updateDescriptionUrl);
    }

}
