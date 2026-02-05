package com.suleymanov.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:/application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.suleymanov.dao")
public class DataBaseConfig {

    @Value("${db.driver}")
    private String databaseDriver;
    @Value("${db.url}")
    private String databaseUrl;
    @Value("${db.username}")
    private String databaseUsername;
    @Value("${db.password}")
    private String databasePassword;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2DdlAuto;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(databaseDriver);
        ds.setUrl(databaseUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.suleymanov.entity"); // ваши сущности
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(hibernateProperties());
        return emf;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.dialect", hibernateDialect);
        props.put("hibernate.show_sql", hibernateShowSql);
        props.put("hibernate.hbm2ddl.auto", hibernateHbm2DdlAuto);
        return props;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf.getObject());
        return tm;
    }
}
