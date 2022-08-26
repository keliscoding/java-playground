package io.github.zam0k.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.zam0k.security.jwt.JwtAuthFilter;
import io.github.zam0k.security.jwt.JwtService;
import io.github.zam0k.service.implementation.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JwtService jwtService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioService);
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
				.antMatchers("/api/clientes/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/api/pedidos/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/api/produtos/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/usuarios/**").permitAll()
				.anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(jwtFilter(),
						UsernamePasswordAuthenticationFilter.class);
		// Session Stateless significa que não há mais sessão, agora toda
		// requisição tem que reconhecer o usuário
		// httpbasic é passando as infos pelo header
	}

}
