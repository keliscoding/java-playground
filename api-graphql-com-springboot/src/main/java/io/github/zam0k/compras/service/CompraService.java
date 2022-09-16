package io.github.zam0k.compras.service;

import io.github.zam0k.compras.graphql.inputs.CompraInput;
import io.github.zam0k.compras.model.Cliente;
import io.github.zam0k.compras.model.Compra;
import io.github.zam0k.compras.model.Produto;
import io.github.zam0k.compras.repository.ClienteRepository;
import io.github.zam0k.compras.repository.CompraRepository;
import io.github.zam0k.compras.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraService {

  private final CompraRepository repository;
  private final ProdutoRepository produtoRepository;
  private final ClienteRepository clienteRepository;
  private final ModelMapper mapper;

  public Compra findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<Compra> findAll() {
    return repository.findAll();
  }

  @Transactional
  public Compra save(CompraInput input) {
    Compra compra = mapper.map(input, Compra.class);

    Produto produto =
        produtoRepository.findById(input.getProdutoId()).orElseThrow(RuntimeException::new);
    Cliente cliente =
        clienteRepository.findById(input.getClienteId()).orElseThrow(RuntimeException::new);

    compra.setCliente(cliente);
    compra.setProduto(produto);

    return repository.save(compra);
  }

  @Transactional
  public Boolean deleteById(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return true;
    }
    return false;
  }
}
