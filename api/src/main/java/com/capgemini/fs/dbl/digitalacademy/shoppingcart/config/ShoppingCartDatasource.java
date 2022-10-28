package com.capgemini.fs.dbl.digitalacademy.shoppingcart.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(ignoreResourceNotFound = false, value = "classpath:datasource.properties")
public class ShoppingCartDatasource {

	@Value("${shoppingcart.datasource.driver.classname}")
	private String driverClassName;
	@Value("${shoppingcart.datasource.url}")
	private String url;
	@Value("${shoppingcart.datasource.username}")
	private String username;
	@Value("${shoppingcart.datasource.password}")
	private String password;

	@Bean
	@Primary
	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(this.driverClassName);
		dataSourceBuilder.url(this.url);
		dataSourceBuilder.username(this.username);
		dataSourceBuilder.password(this.password);
		return dataSourceBuilder.build();
	}
}
