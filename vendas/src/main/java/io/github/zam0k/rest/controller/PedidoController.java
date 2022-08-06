package io.github.zam0k.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.zam0k.service.IPedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private IPedidoService pedidoService;

	@Autowired
	public PedidoController(IPedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

}
