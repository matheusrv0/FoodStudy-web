package com.foodstudy.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foodcash")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodCash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float saldo = 0f;

    // FoodCash pertence a 1 usuário (lado inverso do @OneToOne)
    @OneToOne(mappedBy = "foodCash")
    private Usuario usuario;

    // Métodos do diagrama
    public void adicionar(float valor) {
        this.saldo = (saldo == null ? 0f : saldo) + valor;
    }

    public void descontar(float valor) {
        this.saldo = (saldo == null ? 0f : saldo) - valor;
    }

    public float consultarSaldo() {
        return saldo == null ? 0f : saldo;
    }
}
