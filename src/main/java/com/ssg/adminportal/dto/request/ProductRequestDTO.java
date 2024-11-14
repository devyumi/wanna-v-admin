package com.ssg.adminportal.dto.request;

import com.ssg.adminportal.common.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[0-9]+$", message = "Only numeric values are allowed.")
    private Double costPrice;

    @NotBlank(message = "Please enter the required product selling price.")
    @Pattern(regexp = "^[0-9]+$", message = "Only numeric values are allowed.")
    private Double sellingPrice;

    private Integer discountRate;

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

    public Double getFinalPrice() {
        if (this.sellingPrice != null && this.discountRate != null && this.discountRate > 0) {
            return this.sellingPrice * (1 - this.discountRate / 100.0);
        }
        return this.sellingPrice;
    }

}