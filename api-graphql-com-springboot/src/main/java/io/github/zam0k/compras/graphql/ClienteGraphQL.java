package io.github.zam0k.compras.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import io.github.zam0k.compras.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteGraphQL implements GraphQLQueryResolver, GraphQLMutationResolver {

  private final ClienteService clienteService;

  public Cliente cliente(Long id) {
    return clienteService.findById(id);
  }

  public List<Cliente> clientes() {
    return clienteService.findAll();
  }

  public Cliente saveCliente(Long id, String nome, String email) {
    Cliente c = new Cliente(id, nome, email);
    return clienteService.save(c);
  }

  public Boolean deleteCliente(Long id) {
    return clienteService.deleteById(id);
  }
}
