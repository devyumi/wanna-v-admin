package com.ssg.adminportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssg.adminportal.common.Category;
import com.ssg.adminportal.dto.request.ProductRequestDTO;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(name = "cost_price", nullable = false)
    private Double costPrice;

    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice;

    @Column(name = "discount_rate", nullable = false)
    @ColumnDefault("0")
    private Integer discountRate;

    @Column(name = "final_price", nullable = false)
    private Double finalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> description;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("0")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    @JsonIgnore
    private Admin createdById;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    @JsonIgnore
    private Admin updatedById;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private LocalDateTime updatedAt;

    public void update(ProductRequestDTO requestDTO, String updateImageUrl, List<String> updateDescriptionUrl) {
        this.name = requestDTO.getName();
        this.costPrice = requestDTO.getCostPrice();
        this.sellingPrice = requestDTO.getSellingPrice();
        this.discountRate = requestDTO.getDiscountRate();
        this.finalPrice = requestDTO.getFinalPrice();
        this.category = requestDTO.getCategory();
        this.stock = requestDTO.getStock();
        this.isActive = requestDTO.getIsActive();
        this.updatedAt = requestDTO.getUpdatedAt();

        if (updateImageUrl != null) {
            this.image = updateImageUrl;
        }

        if (updateDescriptionUrl != null) {
            this.description = updateDescriptionUrl;
        }
    }
}
