package com.microsservice.processa_notificacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProcessaNotificacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessaNotificacaoApplication.class, args);
	}

}
