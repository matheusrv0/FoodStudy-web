package com.foodstudy.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produtos")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Float preco;
    private String tipo;

    // Um produto pertence a um único estabelecimento
    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    // ---- Métodos do diagrama ----

    public void adicionarAoCatalogo() {
        // Método a ser implementado futuramente
    }
}
