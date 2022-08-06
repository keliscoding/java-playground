package io.github.zam0k.rest.controller;

import java.util.List;

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

import io.github.zam0k.domain.entity.Produto;
import io.github.zam0k.domain.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	private ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoController(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Produto cadastrarProdutos(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}

	@GetMapping("/{id}")
	public Produto getProdutoById(@PathVariable Integer id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Produto não encontrado"));
	}

	@GetMapping
	public List<Produto> listProdutos(Produto produto) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);

		Example<Produto> example = Example.of(produto, matcher);

		return produtoRepository.findAll(example);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteProduto(@PathVariable Integer id) {
		produtoRepository.findById(id).map(produto -> {
			produtoRepository.delete(produto);
			return void.class;
		}).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}

	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateProduto(@PathVariable Integer id,
			@RequestBody Produto produto) {
		produtoRepository.findById(id).map(produtoFound -> {
			produto.setId(id);
			produtoRepository.save(produto);
			return void.class;
		}).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND));
	}
}
