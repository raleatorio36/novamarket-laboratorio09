package com.novamarket.laboratorio09.pedido;

import com.novamarket.laboratorio09.modelo.Pedido;

/**
 * Implementación base de PedidoServicio.
 * Delega el cálculo al modelo Pedido sin agregar nada extra.
 */
public class PedidoBase implements PedidoServicio {

    private final Pedido pedido;

    public PedidoBase(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String getCodigo() {
        return pedido.getCodigo();
    }

    @Override
    public double getTotal() {
        return pedido.getTotal();
    }

    @Override
    public String getDescripcion() {
        return String.format("Pedido %s — sin servicios adicionales", pedido.getCodigo());
    }
}
