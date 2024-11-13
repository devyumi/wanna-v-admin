package com.ssg.adminportal.dto.request;

import com.ssg.adminportal.common.Category;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String name;
    private List<String> image;
    private Double costPrice;
    private Double sellingPrice;
    private Integer discountRate;
    private Double finalPrice;
    private Category category;
    private Integer stock;
    private String description;
    private Boolean isActive;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

}