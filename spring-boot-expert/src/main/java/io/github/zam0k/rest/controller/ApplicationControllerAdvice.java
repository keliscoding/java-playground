package io.github.zam0k.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.zam0k.exception.PedidoNaoEncontradoException;
import io.github.zam0k.exception.RegraNegocioException;
import io.github.zam0k.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(
			RegraNegocioException exception) {
		String mensagemErro = exception.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(PedidoNaoEncontradoException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ApiErrors handlePedidoNotFoundException(
			PedidoNaoEncontradoException e) {
		return new ApiErrors(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ApiErrors handleMethodNotValidException(
			MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult().getAllErrors()
				.stream().map(erro -> erro.getDefaultMessage())
				.collect(Collectors.toList());

		return new ApiErrors(errors);
	}

}
