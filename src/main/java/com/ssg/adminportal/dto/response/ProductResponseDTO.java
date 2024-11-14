package com.ssg.adminportal.dto.response;

import com.ssg.adminportal.common.Category;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double finalPrice;
    private Category category;
    private Integer stock;
    private Boolean isActive;
    private LocalDateTime createdAt;
}