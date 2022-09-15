package io.github.zam0k.compras.graphql.inputs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoInput {
    private Long id;
    private String nome;
    private BigDecimal valor;
}
