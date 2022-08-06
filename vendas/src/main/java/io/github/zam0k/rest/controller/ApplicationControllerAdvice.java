package io.github.zam0k.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

}
