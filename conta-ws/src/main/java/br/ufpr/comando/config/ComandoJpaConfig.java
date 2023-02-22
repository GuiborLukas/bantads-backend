package br.ufpr.comando.config;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "br.ufpr.comando", entityManagerFactoryRef = "comandoEntityManagerFactory", transactionManagerRef = "comandoTransactionManager")
@EntityScan(basePackages = { "br.ufpr.shared.conta.model", "br.ufpr.shared.movimentacao.model" })
@ComponentScan(basePackages = "br.ufpr.shared")
public class ComandoJpaConfig {

	@Autowired
	Environment env;

	@Primary
	@Bean(name = "comandoDataSource")
	@ConfigurationProperties(prefix = "spring.comando.datasource")
	public DataSource dataSource() {
		System.out.println("Datasource Comando carregando");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.comando.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.comando.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.comando.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.comando.datasource.password"));
		return dataSource;
	}

	@Primary
	@Bean(name = "comandoEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("comandoDataSource") DataSource dataSource) {
		final LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataSource);
		entityManager.setPackagesToScan("br.ufpr.shared.conta.model", "br.ufpr.shared.movimentacao.model");
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(vendorAdapter);
		final HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.ddl-auto", "update");
		properties.put("generate-ddl", "true");
		properties.put("show-sql", "true");
		properties.put("database-platform", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("properties.hibernate.format_sql", "true");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

	@Primary
	@Bean(name = "comandoTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("comandoEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}