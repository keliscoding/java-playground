package io.github.zam0k.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.zam0k.domain.entity.ItemPedido;
import io.github.zam0k.domain.entity.Pedido;
import io.github.zam0k.domain.enums.StatusPedido;
import io.github.zam0k.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.zam0k.rest.dto.InformacaoItemPedidoDTO;
import io.github.zam0k.rest.dto.InformacoesPedidoDTO;
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

	@GetMapping("/{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service.obterPedidoCompleto(id)
				.map(pedido -> converter(pedido))
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Pedido não encontrado."));
	}

	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable Integer id,
			@RequestBody AtualizacaoStatusPedidoDTO dto) {
		String novoStatus = dto.getNovoStatus();
		service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
	}

	private InformacoesPedidoDTO converter(Pedido pedido) {
		return InformacoesPedidoDTO.builder().codigo(pedido.getId())
				.dataPedido(pedido.getDataPedido()
						.format(DateTimeFormatter
								.ofPattern("dd/MM/yyyy HH:mm:ss")))
				.cpf(pedido.getCliente().getCpf())
				.nomeCliente(pedido.getCliente().getNome())
				.total(pedido.getTotal())
				.status(pedido.getStatus().name())
				.items(converter(pedido.getItens())).build();
	}

	private List<InformacaoItemPedidoDTO> converter(
			List<ItemPedido> itens) {
		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList(); // não é uma boa pratica retornar objetos nulos, melhor retornar um vazio
		}

		return itens.stream().map(item -> InformacaoItemPedidoDTO
				.builder()
				.descricaoProduto(item.getProduto().getDescricao())
				.precoUnitario(item.getProduto().getPrecoUnitario())
				.quantidade(item.getQuantidade()).build())
				.collect(Collectors.toList());
	}

}
