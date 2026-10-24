package com.novamarket.laboratorio09.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representa un producto dentro de un pedido.
 */
@Getter
@AllArgsConstructor
public class ItemPedido {

    private final String codigo;
    private final String nombre;
    private final int cantidad;
    private final double precioUnitario;

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
}
