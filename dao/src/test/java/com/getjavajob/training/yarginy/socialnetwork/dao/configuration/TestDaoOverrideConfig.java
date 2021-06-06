package com.getjavajob.training.yarginy.socialnetwork.dao.configuration;

import com.getjavajob.training.yarginy.socialnetwork.common.configuration.CommonConfiguration;
import com.getjavajob.training.yarginy.socialnetwork.dao.DaoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
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
    @Value("${test.datasource.url}")
    private String url;
    @Value("${test.datasource.username}")
    private String user;
    @Value("${test.datasource.driver-class-name}")
    private String driver;
    @Value("${test.datasource.init-script-location}")
    private String initScriptLocation;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setDriverClassName(driver);
        Resource resource = new ClassPathResource(initScriptLocation);
        DatabasePopulator populator = new ResourceDatabasePopulator(resource);
        DatabasePopulatorUtils.execute(populator, dataSource);
        return dataSource;
    }
}