package com.getjavajob.training.yarginy.socialnetwork.dao.configuration;

import com.getjavajob.training.yarginy.socialnetwork.common.configuration.CommonConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(CommonConfiguration.class)
@ComponentScan("com.getjavajob.training.yarginy.socialnetwork.dao")
@EnableJpaRepositories("com.getjavajob.training.yarginy.socialnetwork.dao.repositories")
@PropertySource("classpath:dao.properties")
public class DaoConfiguration {
}
