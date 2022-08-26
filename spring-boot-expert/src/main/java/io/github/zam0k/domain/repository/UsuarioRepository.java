package io.github.zam0k.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.zam0k.domain.entity.Usuario;

public interface UsuarioRepository
		extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByUsername(String username);
}
