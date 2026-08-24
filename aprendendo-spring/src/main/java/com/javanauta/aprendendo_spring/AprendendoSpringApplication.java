package com.javanauta.aprendendo_spring;

import com.javanauta.aprendendo_spring.infrastructure.entity.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AprendendoSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(AprendendoSpringApplication.class, args);

		Usuario joao = new Usuario("Joao", "joaodasilva@hotmail.com", "123156");
		joao.setNome("João Silva");
		System.out.println(joao.getNome());
		joao.setNome("Joao da Silva");
		System.out.println(joao.getEmail());
	}

}
