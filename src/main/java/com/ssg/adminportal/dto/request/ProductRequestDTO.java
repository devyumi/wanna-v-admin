package com.ssg.adminportal.dto.request;

import com.ssg.adminportal.common.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Please enter the required product name.")
    private String name;

    @NotEmpty(message = "Please enter the required product thumbnail.")
    private List<String> image;

    @NotNull(message = "Please enter the required product cost price.")
    @Positive(message = "Selling price must be a positive number.")
    private Double costPrice;

    @NotNull(message = "Please enter the required product selling price.")
    @Positive(message = "Selling price must be a positive number.")
    private Double sellingPrice;

    @Positive(message = "Selling price must be a positive number.")
    private Integer discountRate;

    @NotNull(message = "Please enter the required product category.")
    private Category category;

    @NotNull(message = "Please enter the required product stock.")
    @Positive(message = "Selling price must be a positive number.")
    private Integer stock;

    @NotEmpty(message = "Please enter the required product description.")
    private List<String> description;

    @NotNull(message = "Please enter the product activation status.")
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
