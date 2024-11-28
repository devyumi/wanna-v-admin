package com.ssg.adminportal.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.dto.request.ProductListRequestDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Integer page;
    private Integer size;
    private String sort;
    private Integer total;
    private Integer last;
    private Integer start;
    private Integer end;
    private List<Product> products;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public ProductResponseDTO(ProductListRequestDTO requestDTO, List<Product> products, Integer total) {
        this.page = requestDTO.getPage();
        this.size = requestDTO.getSize();
        this.sort = requestDTO.getSort();
        this.products = products;
        this.total = total;
        this.last = (int) Math.ceil(total / (double) size);
        this.start = (page - 1) / 10 * 10 + 1;
        this.end = (last == 0) ? 1 : Math.min(start + 9, last);
    }
}