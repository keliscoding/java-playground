package io.github.zam0k.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.github.zam0k.domain.entity.Cliente;

//repository são classes que acessam a base de dados

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	// Query Methods
	public List<Cliente> findByNomeLike(String nome);

	// usando @Query, pode usar hql ou sql nativo (nativeQuery = true)
	@Query(value = "select c from Cliente c where c.nome like :nome ")
	public List<Cliente> encontrarPorNome(String nome);

	@Query("delete from Cliente c where c.nome = :nome")
	@Modifying // obrigatório colocar em métodos delete ou update
	public void deleteByNome(String nome);

}
