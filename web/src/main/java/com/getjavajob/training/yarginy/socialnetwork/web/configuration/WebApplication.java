package com.getjavajob.training.yarginy.socialnetwork.web.configuration;

import com.getjavajob.training.yarginy.socialnetwork.service.configuration.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.getjavajob.training.yarginy.socialnetwork.web")
@Import(ServiceConfig.class)
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
