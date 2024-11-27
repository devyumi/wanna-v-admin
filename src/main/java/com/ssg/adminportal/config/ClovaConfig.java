package com.ssg.adminportal.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ClovaConfig {

    @Value("${naver.clova.url}")
    private String url;

    @Value("${naver.clova.api-key}")
    private String apiKey;

    @Value("${naver.clova.apigw-api-key}")
    private String apiGatewayKey;

    @Value("${naver.clova.request-id}")
    private String requestId;

}
