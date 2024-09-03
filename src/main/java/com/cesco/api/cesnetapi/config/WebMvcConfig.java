package com.cesco.api.cesnetapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig
 * @since   2021-07-26
 * @author  조선호
 * @version 2107.1
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    /**
     * addInterceptors
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        // interceptor 할 대상 확인
        registry.addInterceptor(new ApiInterceptor())
                    .addPathPatterns("/**") // 전체
                    .excludePathPatterns("/health"); // health 는 체크하지 않음
    }
}