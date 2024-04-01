package com.example.youtube.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/video/**")
                .addResourceLocations("file:C:\\Users\\hieuh\\IdeaProjects\\tmp\\image\\");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/" );
//        hiển thị các tài nguyên tĩnh
    }

}
