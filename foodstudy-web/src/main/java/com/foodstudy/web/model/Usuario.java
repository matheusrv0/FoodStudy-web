package com.foodstudy.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String tipo;

    // ---- Relacionamentos ----

    // Um usuário possui 1 FoodCash
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "foodcash_id")
    private FoodCash foodCash;

    // Um usuário pode realizar vários pedidos
    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos = new ArrayList<>();

    // Um usuário pode receber várias notificações
    @OneToMany(mappedBy = "usuario")
    private List<Notificacao> notificacoes = new ArrayList<>();

    // Um usuário possui 1 assinatura
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Assinatura assinatura;

    // ---- Métodos do diagrama ----

    public List<Pedido> visualizarPedidos() {
        return pedidos;
    }

    public void adicionarFoodCash(float valor) {
        if (foodCash != null) {
            foodCash.adicionar(valor);
        }
    }

}


