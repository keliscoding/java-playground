package io.github.zam0k.compras.graphql.inputs;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CompraInput {
    private Long id;
    private OffsetDateTime data;
    private Integer quantidade;
    private String status;
    private Long clienteId;
    private Long produtoId;
}
