package com.vision.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by vipul.pant on 7/18/17.
 */
@SpringBootApplication
@ComponentScan("com.vision")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class , args);
    }
}
