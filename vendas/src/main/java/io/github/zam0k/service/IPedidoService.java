package io.github.zam0k.service;

import io.github.zam0k.domain.entity.Pedido;
import io.github.zam0k.rest.dto.PedidoDTO;

public interface IPedidoService {
	Pedido salvar(PedidoDTO pedido);
}
