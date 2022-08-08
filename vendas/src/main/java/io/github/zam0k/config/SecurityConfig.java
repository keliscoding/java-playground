package io.github.zam0k.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.zam0k.service.implementation.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService usuarioService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		// Aqui define a parte de autenticação
		auth.userDetailsService(usuarioService)
				.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Aqui define a parte de autorização
		http.csrf().disable().authorizeRequests()
				.antMatchers("/api/clientes/**")
				.hasAnyRole("USER", "ADMIN")
				.antMatchers("/api/pedidos/**")
				.hasAnyRole("USER", "ADMIN")
				.antMatchers("/api/produtos/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/usuarios/**")
				.permitAll().anyRequest().authenticated().and()
				.httpBasic(); // httpbasic é passando as infos pelo header
	}

}
