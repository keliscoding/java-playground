package io.github.zam0k.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.github.zam0k.domain.entity.Cliente;
import io.github.zam0k.domain.entity.ItemPedido;
import io.github.zam0k.domain.entity.Pedido;
import io.github.zam0k.domain.entity.Produto;
import io.github.zam0k.domain.repository.ClienteRepository;
import io.github.zam0k.domain.repository.ItemPedidoRepository;
import io.github.zam0k.domain.repository.PedidoRepository;
import io.github.zam0k.domain.repository.ProdutoRepository;
import io.github.zam0k.exception.RegraNegocioException;
import io.github.zam0k.rest.dto.ItemPedidoDTO;
import io.github.zam0k.rest.dto.PedidoDTO;
import io.github.zam0k.service.IPedidoService;
import lombok.RequiredArgsConstructor;

//a camada de servico é a camada específica para as regras de negócio

@Service
@RequiredArgsConstructor
public class PedidoService implements IPedidoService {

	private final PedidoRepository pedidoRepository;
	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;
	private final ItemPedidoRepository itemPedidoRepository;

	@Override
	@Transactional // garante que ou salva tudo com sucesso no db ou se houver erro em algo, ele da
					// um rollback em tudo
	public Pedido salvar(PedidoDTO dto) {
		Pedido pedido = new Pedido();
		Integer idCliente = dto.getCliente();
		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new RegraNegocioException(
						"Código do cliente inválido."));

		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setCliente(cliente);

		List<ItemPedido> itemsPedido = converterItems(pedido,
				dto.getItems());
		pedidoRepository.save(pedido);
		itemPedidoRepository.saveAll(itemsPedido);

		pedido.setItens(itemsPedido);

		return pedido;
	}

	private List<ItemPedido> converterItems(Pedido pedido,
			List<ItemPedidoDTO> items) {
		if (items.isEmpty()) {
			throw new RegraNegocioException(
					"Não é possível realizar um pedido sem items.");
		}

		return items.stream().map(dto -> {
			Integer produtoId = dto.getProduto();
			Produto produto = produtoRepository.findById(produtoId)
					.orElseThrow(() -> new RegraNegocioException(
							"Código do produto inválido."));

			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(dto.getQuantidade());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			return itemPedido;
		}).collect(Collectors.toList()); // isso aqui converte o objeto stream pra lista
	}

}
