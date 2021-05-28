package com.getjavajob.training.yarginy.socialnetwork.service.configuration;

import com.getjavajob.training.yarginy.socialnetwork.dao.configuration.DaoConfiguration;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(DaoConfiguration.class)
@ComponentScan("com.getjavajob.training.yarginy.socialnetwork.service")
@EnableTransactionManagement
public class ServiceConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public XStream xStream() {
        return new XStream(new Dom4JDriver());
    }
}
