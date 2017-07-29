package com.vision.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfigurer extends WebMvcConfigurerAdapter {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/WEB-INF/**").addResourceLocations(
                "/WEB-INF/");//
        registry.addResourceHandler("/WEB-INF/views/**").addResourceLocations(
                "/WEB-INF/views/");//
        registry.addResourceHandler("/json/**").addResourceLocations(
                "/WEB-INF/app/json/");//
        registry.addResourceHandler("/app/**").addResourceLocations(
                "/WEB-INF/app/");//
        registry.addResourceHandler("/app/css/**").addResourceLocations(
                "/WEB-INF/app/css/");//
        registry.addResourceHandler("/app/css/vendor/**").addResourceLocations(
                "/WEB-INF/app/css/vendor/");//
        registry.addResourceHandler("/build/**").addResourceLocations(
                "/WEB-INF/build/");//
        registry.addResourceHandler("/placeholders/**").addResourceLocations(
                "/WEB-INF/app/placeholders/");//
        registry.addResourceHandler("/js/**").addResourceLocations("/app/js/");//
        registry.addResourceHandler("/images/**").addResourceLocations(
                "/WEB-INF/app/images/");//
        registry.addResourceHandler("/favicon/**").addResourceLocations(
                "/favicon/");//
        registry.addResourceHandler("/static/**").addResourceLocations(
                "/static/");
        registry.addResourceHandler("**/static/images/**")
                .addResourceLocations("/static/images/");
    }
}