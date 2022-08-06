package io.github.zam0k.service.implementation;

import org.springframework.stereotype.Service;

import io.github.zam0k.domain.repository.PedidoRepository;
import io.github.zam0k.service.IPedidoService;

//a camada de servico é a camada específica para as regras de negócio

@Service
public class PedidoService implements IPedidoService {

	private PedidoRepository pedidoRepository;

	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

}
