package com.novamarket.laboratorio09.pedido;

/**
 * PATRÓN DECORATOR — RF-05
 * Agrega el servicio de atención prioritaria: + S/ 12.00.
 */
public class DecoradorPrioridad extends DecoradorPedido {

    private static final double COSTO_PRIORIDAD = 12.00;

    public DecoradorPrioridad(PedidoServicio pedidoDecorado) {
        super(pedidoDecorado);
    }

    @Override
    public double getTotal() {
        return pedidoDecorado.getTotal() + COSTO_PRIORIDAD;
    }

    @Override
    public String getDescripcion() {
        return pedidoDecorado.getDescripcion() + " + Prioridad (S/ 12.00)";
    }
}
