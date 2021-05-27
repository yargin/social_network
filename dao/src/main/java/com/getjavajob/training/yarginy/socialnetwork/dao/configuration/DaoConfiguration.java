package com.getjavajob.training.yarginy.socialnetwork.dao.configuration;

import com.getjavajob.training.yarginy.socialnetwork.common.configuration.CommonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(CommonConfiguration.class)
@ComponentScan({"com.getjavajob.training.yarginy.socialnetwork.dao"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories("com.getjavajob.training.yarginy.socialnetwork.dao.repositories")
public class DaoConfiguration {
    @Bean
    public JndiDataSourceLookup jndiDataSourceLookup() {
        return new JndiDataSourceLookup();
    }

    @Bean
    public DataSource dataSource() {
        return jndiDataSourceLookup().getDataSource("jdbc/DbConnection");
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(false);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
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

    @Bean
    public TransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
