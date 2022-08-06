package io.github.zam0k.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

	public PedidoNaoEncontradoException() {
		super("Pedido não encontrado.");
	}

}
