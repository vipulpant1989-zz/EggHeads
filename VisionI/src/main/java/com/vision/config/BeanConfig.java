package com.vision.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 */
@Configuration
public class BeanConfig {


    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplate(){

        RestTemplate template = new RestTemplate();
        template.setMessageConverters(getMessageConverter());
        return template;

    }

    private List<HttpMessageConverter<?>> getMessageConverter(){
        HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
        return Collections.singletonList(httpMessageConverter) ;
    }


}
