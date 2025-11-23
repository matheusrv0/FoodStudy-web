package com.foodstudy.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assinaturas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private Float valor;
    private String beneficios;

    // Uma assinatura pertence a um usuário
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // ---- Método do diagrama ----
    public void ativar() {
        // Lógica será implementada futuramente
    }
}
