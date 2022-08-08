package io.github.zam0k.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.zam0k.domain.entity.Usuario;
import io.github.zam0k.service.implementation.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	private final UsuarioService usuarioService;

	private final PasswordEncoder passwordEncoder;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario salvar(@RequestBody @Valid Usuario usuario) {
		String senhaCriptografada = passwordEncoder
				.encode(usuario.getPassword());
		usuario.setPassword(senhaCriptografada);
		return usuarioService.salvar(usuario);
	}
}
