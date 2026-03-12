package com.hana8.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${upload.path}")
	private String uploadPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 소스 폴더(src) 내부의 파일을 실시간으로 읽어오기 위한 설정
        // uploadPath가 "src/main/resources/static/upload"일 때, 앞에 "file:"을 붙여 실제 파일 시스템을 찌름
		registry.addResourceHandler("/upload/**")
			.addResourceLocations("file:" + uploadPath + "/");
	}

}
