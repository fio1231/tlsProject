package com.tls.enc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:enc.properties")
@SpringBootApplication
public class EncApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncApplication.class, args);
    }

}
