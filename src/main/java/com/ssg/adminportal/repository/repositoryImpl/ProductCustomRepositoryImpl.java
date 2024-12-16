package com.ssg.adminportal.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.adminportal.domain.Product;
import com.ssg.adminportal.domain.QProduct;
import com.ssg.adminportal.dto.request.ProductListRequestDTO;
import com.ssg.adminportal.repository.ProductCustomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;

    @Override
    public List<Product> findAll(ProductListRequestDTO requestDTO) {

        JPAQuery<Product> result = queryFactory.selectFrom(product);

        processWhere(result, requestDTO);

        return result
            .offset(requestDTO.getOffset())
            .limit(requestDTO.getSize())
            .fetch();
    }

    @Override
    public Integer count(ProductListRequestDTO requestDTO) {
        JPAQuery<Long> result = queryFactory.select(product.count())
            .from(product);

        processWhere(result, requestDTO);
        return result.fetchFirst().intValue();
    }


    public void processWhere(JPAQuery<?> query, ProductListRequestDTO requestDTO) {
        if (requestDTO.getKeyword() == "" || requestDTO.getKeyword() == null) {
            if (requestDTO.getSort() == null) {
                query.orderBy(product.id.desc());
            } else if (requestDTO.getSort().equals("new")) {
                query.orderBy(product.id.desc());
            } else if (requestDTO.getSort().equals("name")) {
                query.orderBy(product.name.asc());
            } else if (requestDTO.getSort().equals("priceDesc")) {
                query.orderBy(product.finalPrice.desc());
            } else if (requestDTO.getSort().equals("priceAsc")) {
                query.orderBy(product.finalPrice.asc());
            }
        } else {
            query.where(product.name.contains(requestDTO.getKeyword()))
                .orderBy(product.id.desc());
        }
    }
}
