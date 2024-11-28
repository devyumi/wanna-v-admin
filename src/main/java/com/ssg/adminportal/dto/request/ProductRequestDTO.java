package com.ssg.adminportal.dto.request;

import com.ssg.adminportal.common.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Please enter the required product name.")
    private String name;

    private MultipartFile image;

    @NotNull(message = "Please enter the required product cost price.")
    @Positive(message = "Cost price must be a positive number.")
    private Double costPrice;

    @NotNull(message = "Please enter the required product selling price.")
    @Positive(message = "Selling price must be a positive number.")
    private Double sellingPrice;

    @Positive(message = "Discount rate must be a positive number.")
    private Integer discountRate;

    @NotNull(message = "Please enter the required product category.")
    private Category category;

    @NotNull(message = "Please enter the required product stock.")
    @Positive(message = "Product stock must be a positive number.")
    private Integer stock;

    @Size(min = 1, max =3, message = "You can upload between 1 and 3 description images.")
    private List<MultipartFile> description;

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

    public void multipartImage(MultipartFile image) {
        this.image = image;
    }

    public void multipartDescription(List<MultipartFile> description) {
        this.description = description;
    }
}
