package io.github.zam0k.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.zam0k.domain.entity.Pedido;

public interface PedidoRepository
		extends JpaRepository<Pedido, Integer> {

}
