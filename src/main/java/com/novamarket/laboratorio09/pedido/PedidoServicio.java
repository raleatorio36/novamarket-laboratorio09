package com.novamarket.laboratorio09.pedido;

/**
 * Contrato común para el pedido base y sus decoradores.
 * (RF-05) Permite añadir servicios opcionales sin modificar el
 * comportamiento base ni copiar lógica de cálculo.
 */
public interface PedidoServicio {

    /** Código del pedido. */
    String getCodigo();

    /** Total calculado (base + servicios opcionales acumulados). */
    double getTotal();

    /** Descripción legible de los servicios incluidos. */
    String getDescripcion();
}
