package io.github.zam0k.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.zam0k.domain.entity.Cliente;
import io.github.zam0k.domain.repository.ClienteRepository;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

	private ClienteRepository clienteRepository;

	@Autowired
	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Cliente> getClienteById(
			@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<Cliente> createCliente(
			@RequestBody Cliente cliente) {
		clienteRepository.save(cliente);

		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Cliente> removeCliente(
			@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isPresent()) {
			clienteRepository.delete(cliente.get());
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> updateCliente(@PathVariable Integer id,
			@RequestBody Cliente cliente) {
		return clienteRepository.findById(id)
				.map(clienteExistente -> {
					cliente.setId(id);
					clienteRepository.save(cliente);
					return ResponseEntity.noContent().build();
				}).orElseGet(() -> ResponseEntity.notFound().build());

	}

	@GetMapping
	public ResponseEntity<List<Cliente>> findCliente(Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);

		Example<Cliente> example = Example.of(filtro, matcher);
		List<Cliente> lista = clienteRepository.findAll(example);

		return ResponseEntity.ok(lista);
	}
}
