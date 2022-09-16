package io.github.zam0k.compras.graphql;

import io.github.zam0k.compras.graphql.inputs.CompraInput;
import io.github.zam0k.compras.model.Compra;
import io.github.zam0k.compras.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompraGraphQL {

  private final CompraService service;

  @QueryMapping
  public Compra compra(@Argument Long id) {
    return service.findById(id);
  }

  @QueryMapping
  public List<Compra> compras() {
    return service.findAll();
  }

  @MutationMapping
  public Compra saveCompra(@Argument("compra") CompraInput input) {
    return service.save(input);
  }

  @MutationMapping
  public Boolean deleteCompra(Long id) {
    return service.deleteById(id);
  }
}
