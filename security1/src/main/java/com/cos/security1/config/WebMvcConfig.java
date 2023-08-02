package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override //머스테치 재설정!
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new MustacheViewResolver();
		resolver.setCharset("UTF-8"); //인코딩은 UTF-8
		resolver.setContentType("text/html;charset=UTF-8"); //내가 던지는 파일은 html 파일이고 그건 UTF-8로 되어 있음
		resolver.setPrefix("classpath:/templates/"); //classpath = 내 프로젝트 경로 전부 
		resolver.setSuffix(".html"); //이렇게 하면 html 파일을 머스테치가 인식함
		
		registry.viewResolver(resolver); //마지막으로 레지스터로 뷰리졸브를 등록함
	}
}
