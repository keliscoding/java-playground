package io.github.zam0k.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.zam0k.domain.entity.Usuario;
import io.github.zam0k.exception.SenhaInvalidaException;
import io.github.zam0k.rest.dto.CredenciaisDTO;
import io.github.zam0k.rest.dto.TokenDTO;
import io.github.zam0k.security.jwt.JwtService;
import io.github.zam0k.service.implementation.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	private final UsuarioService usuarioService;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario salvar(@RequestBody @Valid Usuario usuario) {
		String senhaCriptografada = passwordEncoder
				.encode(usuario.getPassword());
		usuario.setPassword(senhaCriptografada);
		return usuarioService.salvar(usuario);
	}

	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
		try {
			Usuario usuario = Usuario.builder()
					.username(credenciais.getUsername())
					.password(credenciais.getPassword()).build();

			UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);

			String token = jwtService.gerarToken(usuario);

			return new TokenDTO(usuario.getUsername(), token);

		} catch (UsernameNotFoundException | SenhaInvalidaException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
					e.getMessage());
		}
	}

}
