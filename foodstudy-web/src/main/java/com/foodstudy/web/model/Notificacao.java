package com.foodstudy.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "notificacoes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    // Uma notificação pertence a um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // ---- Método do diagrama ----
    public void enviar() {
        // Lógica futura para envio real
        // Ex: enviar por email, push notification, etc.
    }

}
