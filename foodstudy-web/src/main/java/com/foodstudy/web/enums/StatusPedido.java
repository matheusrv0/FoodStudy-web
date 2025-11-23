package com.foodstudy.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedido {
    PENDENTE("Pendente", false),
    CONFIRMADO("Confirmado", false),
    RETIRADO("Retirado", true),
    CANCELADO("Cancelado", true),
    NAO_RETIRADO("Não retirado", true);

    private final String descricao;
    private final boolean finalizado;

    public boolean podeAlterar() {
        // só deixa alterar se ainda não for finalizado
        return !finalizado;
    }
}
