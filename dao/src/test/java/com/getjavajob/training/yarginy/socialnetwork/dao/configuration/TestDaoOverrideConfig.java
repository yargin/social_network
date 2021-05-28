package com.getjavajob.training.yarginy.socialnetwork.dao.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(DaoConfiguration.class)
@PropertySource("classpath:H2Connection.properties")
public class TestDaoOverrideConfig {
    @Primary
    @Bean
    public DataSource dataSource(@Value("${url}") String url, @Value("${user}") String user,
                                 @Value("${driver}") String driver) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setDriverClassName(driver);
        Resource resource = new ClassPathResource("H2scripts/creation_script.sql");
        DatabasePopulator populator = new ResourceDatabasePopulator(resource);
        DatabasePopulatorUtils.execute(populator, dataSource);
        return dataSource;
    }

    @Primary
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return jpaVendorAdapter;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.getjavajob.training.yarginy.socialnetwork.common.models");
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaProperties(entityManagerProperties());
        return entityManagerFactory;
    }

    //    todo spring boot - wire properties automatically
    private Properties entityManagerProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.physical_naming_strategy",
                "com.getjavajob.training.yarginy.socialnetwork.common.utils.CamelCaseToSnakeCaseNamingStrategy");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.globally_quoted_identifiers", "true");
        return properties;
    }
}