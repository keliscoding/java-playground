package io.github.zam0k.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.zam0k.domain.entity.Cliente;
import io.github.zam0k.domain.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private ClienteRepository clienteRepository;

	@Autowired
	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/{id}")
	public Cliente getClienteById(@PathVariable Integer id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Cliente não encontrado"));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente createCliente(
			@RequestBody @Valid Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCliente(@PathVariable Integer id) {
		clienteRepository.findById(id).map(cliente -> {
			clienteRepository.delete(cliente);
			return void.class;
		}).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Cliente não encontrado"));

	}

	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateCliente(@PathVariable Integer id,
			@RequestBody Cliente cliente) {
		clienteRepository.findById(id).map(clienteExistente -> {
			cliente.setId(id);
			clienteRepository.save(cliente);
			return void.class;
		}).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Cliente não encontrado"));

	}

	@GetMapping
	public List<Cliente> findCliente(Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);

		Example<Cliente> example = Example.of(filtro, matcher);
		return clienteRepository.findAll(example);

	}
}
