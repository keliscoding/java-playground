package io.github.zam0k.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.zam0k.domain.entity.ItemPedido;

public interface ItemPedidoRepository
		extends JpaRepository<ItemPedido, Integer> {

}
