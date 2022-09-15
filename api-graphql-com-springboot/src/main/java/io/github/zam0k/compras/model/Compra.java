package io.github.zam0k.compras.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private LocalDateTime data;
    private Integer quantidade;
    private String status;
    @ManyToOne
    private Cliente cliente;
    @OneToMany
    private List<Produto> produto;
}
