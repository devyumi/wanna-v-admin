package com.ssg.adminportal.dto.request;

import com.ssg.adminportal.common.Category;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Please enter the required product name.")
    private String name;

    @NotBlank(message = "Please enter the required product thumbnail.")
    private List<String> image;

    @NotBlank(message = "Please enter the required product cost price.")
    private Double costPrice;

    @NotBlank(message = "Please enter the required product selling price.")
    private Double sellingPrice;

    private Integer discountRate;

    @NotBlank(message = "Please enter the required product final price.")
    private Double finalPrice;

    @NotBlank(message = "Please enter the required product category.")
    private Category category;

    @NotBlank(message = "Please enter the required product stock.")
    private Integer stock;

    @NotBlank(message = "Please enter the required product description.")
    private List<String> description;

    @NotBlank(message = "Please enter the product activation status.")
    private Boolean isActive;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

}