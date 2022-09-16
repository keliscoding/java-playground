package io.github.zam0k.compras.graphql;

import io.github.zam0k.compras.graphql.inputs.ClienteInput;
import io.github.zam0k.compras.model.Cliente;
import io.github.zam0k.compras.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClienteGraphQL {

  private final ClienteService clienteService;

  @QueryMapping
  public Cliente cliente(@Argument Long id) {
    return clienteService.findById(id);
  }

  @QueryMapping
  public List<Cliente> clientes() {
    return clienteService.findAll();
  }

  @MutationMapping
  public Cliente saveCliente(@Argument("cliente")  ClienteInput input) {
    return clienteService.save(input);
  }

  @MutationMapping
  public Boolean deleteCliente(Long id) {
    return clienteService.deleteById(id);
  }
}
