package io.github.zam0k.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.zam0k.domain.entity.Cliente;

//repository são classes que acessam a base de dados

public interface Clientes extends JpaRepository<Cliente, Integer> {

	// Query Methods
	public List<Cliente> findByNomeLike(String nome);

}
