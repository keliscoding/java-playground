package io.github.zam0k;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "development") // isso aqui faz com que essa config só funcione no ambiente de desenvolvimento
//convenção é alguma coisa configuration, ex: WebConfiguration, SecurityConfiguration
public class MinhaConfiguration {

	@Bean(name = "applicationName")
	public String applicationName() {
		return "Sistema de Vendas";
	}
}
