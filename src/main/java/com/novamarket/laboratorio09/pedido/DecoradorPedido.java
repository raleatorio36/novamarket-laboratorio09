package com.novamarket.laboratorio09.pedido;

/**
 * PATRÓN DECORATOR — clase base abstracta.
 * Envuelve un PedidoServicio y delega las operaciones,
 * permitiendo que las subclases agreguen su comportamiento.
 */
public abstract class DecoradorPedido implements PedidoServicio {

    protected final PedidoServicio pedidoDecorado;

    protected DecoradorPedido(PedidoServicio pedidoDecorado) {
        this.pedidoDecorado = pedidoDecorado;
    }

    @Override
    public String getCodigo() {
        return pedidoDecorado.getCodigo();
    }

    @Override
    public double getTotal() {
        return pedidoDecorado.getTotal();
    }

    @Override
    public String getDescripcion() {
        return pedidoDecorado.getDescripcion();
    }
}
