package br.ufpr;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import br.ufpr.shared.conta.model.Conta;
import br.ufpr.shared.movimentacao.model.Movimentacao;

@SpringBootApplication
public class ContaWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContaWsApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
	return new ModelMapper();
	}
	
	@Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
	   return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
	}
	
	@Bean
	public Conta conta() {
	return new Conta();
	}
	
	@Bean
	public Movimentacao movimentacao() {
	return new Movimentacao();
	}
	
}
