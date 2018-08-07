package com.sap.refapps.espm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
@ComponentScan("com.sap.refapps.espm")
@EnableWebMvc
public class CorsConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/info/**")
	   	  .allowedOrigins("https://product-ui.herokuapp.com/", "http://localhost:8787")
		  .allowedMethods("POST", "GET",  "PUT", "OPTIONS", "DELETE")
		  .allowCredentials(false)
		  .maxAge(4800);
	}
}
