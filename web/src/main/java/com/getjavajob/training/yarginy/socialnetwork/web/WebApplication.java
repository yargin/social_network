package com.getjavajob.training.yarginy.socialnetwork.web;

import com.getjavajob.training.yarginy.socialnetwork.service.configuration.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Import(ServiceConfig.class)
public class WebApplication {
    private final Environment environment;

    public WebApplication(Environment environment) {
        this.environment = environment;
        System.out.println(environment.getProperty("========================================================================="));
        System.out.println(environment.getProperty("PORT"));
        System.out.println(environment.getProperty("========================================================================="));
    }

    public static void main(String[] args) {

        SpringApplication.run(WebApplication.class, args);
    }
}
