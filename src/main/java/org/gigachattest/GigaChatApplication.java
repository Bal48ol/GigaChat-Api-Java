package org.gigachattest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.gigachattest.feignclients")
public class GigaChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(GigaChatApplication.class, args);
    }
}