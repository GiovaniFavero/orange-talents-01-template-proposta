package br.com.zup.propostas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableFeignClients
public class PropostasApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropostasApplication.class, args);

	}
}
