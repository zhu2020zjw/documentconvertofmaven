package com.zjw.convert.util;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@EnableWebMvc
//@Configuration
public class MyWebConfig {//extends WebMvcConfigurerAdapter {

//	@Value("${upload.path}")
//	private String path;
//	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/templates/**")
//		.addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
//         registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
//		// 添加的部分 start
//		registry.addResourceHandler("/add/**") 
//				.addResourceLocations(ResourceUtils.FILE_URL_PREFIX + path);
//		System.out.println(ResourceUtils.FILE_URL_PREFIX );
//		// 添加的部分 end
//		super.addResourceHandlers(registry);
//	}
}
