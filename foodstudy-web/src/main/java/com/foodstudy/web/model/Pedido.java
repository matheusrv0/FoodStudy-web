package com.foodstudy.web.model;

import com.foodstudy.web.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Um pedido pertence a um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Um pedido é referente a um único produto
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    // Um pedido é retirado em um estabelecimento
    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    private LocalDateTime horarioRetirada;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private LocalDateTime criadoEm = LocalDateTime.now();

    // ---- Métodos do diagrama ----

    public void agendarRetirada(LocalDateTime novaData) {
        this.horarioRetirada = novaData;
    }

    public void cancelar() {
        this.status = StatusPedido.CANCELADO;
    }
}
