package com.chy.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 交叉配置
 *
 * @author pixel-revolve
 * @date 2022/05/21
 */
@Configuration
public class CrossConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("*")
                .allowedOrigins("http://10.15.120.205"+":9528")
                .allowedOrigins("http://localhost"+"9529")
                .allowedOrigins("app://.");
//                .allowCredentials(true);
    }

}
