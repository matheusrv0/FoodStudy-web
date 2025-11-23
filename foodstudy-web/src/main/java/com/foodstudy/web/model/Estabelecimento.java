package com.foodstudy.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estabelecimentos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    // Um estabelecimento tem vários produtos
    @OneToMany(mappedBy = "estabelecimento")
    private List<Produto> produtos = new ArrayList<>();

    // Um estabelecimento possui vários pedidos
    @OneToMany(mappedBy = "estabelecimento")
    private List<Pedido> pedidos = new ArrayList<>();

    // Um estabelecimento possui vários relatórios
    @OneToMany(mappedBy = "estabelecimento")
    private List<Relatorio> relatorios = new ArrayList<>();

    // ---- Métodos do diagrama ----

    public void cadastrarProduto(Produto produto) {
        produtos.add(produto);
        // garante consistência do outro lado da relação
        produto.setEstabelecimento(this);
    }

    public void processarPedido(Pedido pedido) {
        pedidos.add(pedido);
        // garante consistência do outro lado da relação
        pedido.setEstabelecimento(this);
    }
}
