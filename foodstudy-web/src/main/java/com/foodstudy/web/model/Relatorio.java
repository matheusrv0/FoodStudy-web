package com.foodstudy.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "relatorios")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private LocalDateTime data;

    @Column(columnDefinition = "TEXT")
    private String conteudo;

    // Cada relatório pertence a um estabelecimento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;

    // ---- Método do diagrama ----
    public void gerar() {
        // A lógica de geração será implementada futuramente
        this.data = LocalDateTime.now();
    }
}
