package org.ISKor.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = "org.ISKor.controller.repositories")
@EntityScan(basePackages = "org.ISKor.controller.entities")
@SpringBootApplication(scanBasePackages = {"org.ISKor.controller"})
public class KittyApplication {
    public static void main(String[] args) {
        SpringApplication.run(KittyApplication.class, args);
    }
}
