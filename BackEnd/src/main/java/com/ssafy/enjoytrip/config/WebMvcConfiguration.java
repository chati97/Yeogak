package com.ssafy.enjoytrip.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.enjoytrip.interceptor.ConfirmInterceptor;

@Configuration
@MapperScan(basePackages = {"com.ssafy.**.mapper"})
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	private final List<String> patterns = Arrays.asList("/tour/myplan", "/user/info", "/user/modify");

	@Autowired
	private ConfirmInterceptor confirmInterceptor;
	
//	@Value("${file.path}")
//	private String uploadPath;
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/upload/image/**").addResourceLocations("file://c:/aaa/aa");
//	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(confirmInterceptor).addPathPatterns(patterns);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	
	
}
