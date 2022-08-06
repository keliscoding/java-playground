package io.github.zam0k.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.zam0k.domain.entity.Pedido;
import io.github.zam0k.rest.dto.PedidoDTO;
import io.github.zam0k.service.IPedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private IPedidoService service;

	@Autowired
	public PedidoController(IPedidoService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Integer save(@RequestBody PedidoDTO dto) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}

}
