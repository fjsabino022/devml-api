package dev.fsabino.devml_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DevMlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevMlApiApplication.class, args);
    }
}