package com.ae.caffeserver;

/**
 * Created by ae on 10-4-16.
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAutoConfiguration
@SpringBootApplication
@Configuration
public class App  {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

