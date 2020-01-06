package com.daffodil.renters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.daffodil.renters.core.model")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
