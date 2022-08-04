package io.github.zam0k;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//convenção é alguma coisa configuration, ex: WebConfiguration, SecurityConfiguration
public class MinhaConfiguration {

	@Bean(name = "applicationName")
	public String applicationName() {
		return "Sistema de Vendas";
	}
}
