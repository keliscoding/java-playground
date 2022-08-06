package io.github.zam0k.service;

import java.util.Optional;

import io.github.zam0k.domain.entity.Pedido;
import io.github.zam0k.domain.enums.StatusPedido;
import io.github.zam0k.rest.dto.PedidoDTO;

public interface IPedidoService {
	Pedido salvar(PedidoDTO pedido);

	Optional<Pedido> obterPedidoCompleto(Integer id);

	void atualizaStatus(Integer id, StatusPedido statusPedido);
}
