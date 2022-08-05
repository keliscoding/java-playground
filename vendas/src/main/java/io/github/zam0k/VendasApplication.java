package io.github.zam0k;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.zam0k.domain.entity.Cliente;
import io.github.zam0k.domain.repository.ClienteRepository;

@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner commandLineRunner(
			@Autowired final ClienteRepository clienteRepository) {
		return new CommandLineRunner() {
			public void run(String... args) throws Exception {
				Cliente c = new Cliente(null, "Kellynha");
				clienteRepository.save(c);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
}
