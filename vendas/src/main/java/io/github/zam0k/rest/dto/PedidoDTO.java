package io.github.zam0k.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.github.zam0k.validation.NotEmptyList;

//serve pra mapear o objeto

//	{
//	    "cliente": 1,
//	    "total": 100,
//	    "items": [
//	        {
//	            "produto": 1,
//	            "quantidade": 10
//	        }
//	    ]
//	}

public class PedidoDTO {
	@NotNull(message = "Informe o código do cliente.")
	private Integer cliente;
	@NotNull(message = "Campo Total do pedido é obrigatório.")
	private BigDecimal total;
	@NotEmptyList(message = "Pedido não pode ser realizado sem itens.")
	private List<ItemPedidoDTO> items;

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<ItemPedidoDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemPedidoDTO> items) {
		this.items = items;
	}

}
