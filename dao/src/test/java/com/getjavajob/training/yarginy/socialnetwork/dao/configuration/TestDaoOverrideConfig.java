package com.getjavajob.training.yarginy.socialnetwork.dao.configuration;

import com.getjavajob.training.yarginy.socialnetwork.common.configuration.CommonConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootConfiguration
@EnableAutoConfiguration
@PropertySource("classpath:testDao.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(CommonConfiguration.class)
@ComponentScan(basePackages = {"com.getjavajob.training.yarginy.socialnetwork.dao"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DaoConfiguration.class)})
@EnableTransactionManagement
@EnableJpaRepositories("com.getjavajob.training.yarginy.socialnetwork.dao.repositories")
public class TestDaoOverrideConfig {
    @Primary
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MYSQL;DATABASE_TO_UPPER=false");
        dataSource.setUsername("sa");
        dataSource.setDriverClassName("org.h2.Driver");
        Resource resource = new ClassPathResource("H2scripts/creation_script.sql");
        DatabasePopulator populator = new ResourceDatabasePopulator(resource);
        DatabasePopulatorUtils.execute(populator, dataSource);
        return dataSource;
    }
}