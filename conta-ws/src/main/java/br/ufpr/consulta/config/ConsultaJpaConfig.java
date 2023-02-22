package br.ufpr.consulta.config;

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
@EnableJpaRepositories(basePackages = "br.ufpr.consulta", entityManagerFactoryRef = "consultaEntityManagerFactory", transactionManagerRef = "consultaTransactionManager")
@EntityScan(basePackages = { "br.ufpr.shared.conta.model", "br.ufpr.shared.movimentacao.model" })
@ComponentScan(basePackages = "br.ufpr.shared")
public class ConsultaJpaConfig {

	@Autowired
	Environment env;

	@Primary
	@Bean(name = "consultaDataSource")
	@ConfigurationProperties(prefix = "spring.consulta.datasource")
	public DataSource dataSource() {
		System.out.println("Datasource Consulta carregando");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.consulta.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.consulta.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.consulta.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.consulta.datasource.password"));
		return dataSource;
	}

	@Primary
	@Bean(name = "consultaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("consultaDataSource") DataSource dataSource) {
		final LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataSource);
		entityManager.setPackagesToScan("br.ufpr.shared.conta.model", "br.ufpr.shared.movimentacao.model");
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(vendorAdapter);
		final HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.ddl-auto", env.getProperty("hibernate.ddl-auto"));
		properties.put("generate-ddl", env.getProperty("generate-ddl"));
		properties.put("show-sql", env.getProperty("show-sql"));
		properties.put("database-platform", env.getProperty("database-platform"));
		properties.put("properties.hibernate.dialect", env.getProperty("properties.hibernate.dialect"));
		properties.put("properties.hibernate.format_sql", env.getProperty("properties.hibernate.format_sql"));
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

	@Primary
	@Bean(name = "consultaTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("consultaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}