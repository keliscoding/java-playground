package io.github.zam0k.rest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

}
