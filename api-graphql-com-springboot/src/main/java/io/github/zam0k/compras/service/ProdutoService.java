package io.github.zam0k.compras.service;

import io.github.zam0k.compras.graphql.inputs.ProdutoInput;
import io.github.zam0k.compras.model.Produto;
import io.github.zam0k.compras.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Produto save(ProdutoInput input) {
        ModelMapper mapper = new ModelMapper();
        Produto produto = mapper.map(input, Produto.class);
        return repository.save(produto);
    }

    @Transactional
    public Boolean deleteById(Long id) {
        if(repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
