package io.github.zam0k.compras.graphql.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//basicamente um DTO...
public class ClienteInput {
    private Long id;
    private String nome;
    private String email;
}
