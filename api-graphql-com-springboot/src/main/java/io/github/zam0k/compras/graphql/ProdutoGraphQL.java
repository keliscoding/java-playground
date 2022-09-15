package io.github.zam0k.compras.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import io.github.zam0k.compras.graphql.inputs.ProdutoInput;
import io.github.zam0k.compras.model.Produto;
import io.github.zam0k.compras.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProdutoGraphQL implements GraphQLQueryResolver, GraphQLMutationResolver {
    private final ProdutoService service;

    public Produto produto(Long id) {
        return service.findById(id);
    }

    public List<Produto> produtos() {
        return service.findAll();
    }

    public Produto saveProduto(ProdutoInput input) {
        return service.save(input);
    }

    public Boolean deleteProduto(Long id) {
        return service.deleteById(id);
    }
}
