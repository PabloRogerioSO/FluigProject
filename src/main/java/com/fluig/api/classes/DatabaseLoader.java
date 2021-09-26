package com.fluig.api.classes;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class DatabaseLoader {

	@Bean
	CommandLineRunner init(VeiculoRepository repository) {

		return args -> {
			repository.save(new Veiculo("Carro", "Chevrolet", "Tracker", LocalDate.of(2000, 11, 13), 1, 1));
			repository.save(new Veiculo("carro", "Fiat", "Argo", LocalDate.of(1999, 10, 10), 4, 4));			
		};
	}
}
