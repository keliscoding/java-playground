package io.github.zam0k.compras.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private OffsetDateTime data;
    private Integer quantidade;
    private String status;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @PrePersist
    public void insertData() {
        this.data = OffsetDateTime.now();
    }
}
