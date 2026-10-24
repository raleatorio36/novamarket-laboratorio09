package com.novamarket.laboratorio09.pedido;

/**
 * PATRÓN DECORATOR — RF-05
 * Agrega el servicio de empaque de regalo: + S/ 8.00.
 */
public class DecoradorEmpaqueRegalo extends DecoradorPedido {

    private static final double COSTO_EMPAQUE = 8.00;

    public DecoradorEmpaqueRegalo(PedidoServicio pedidoDecorado) {
        super(pedidoDecorado);
    }

    @Override
    public double getTotal() {
        return pedidoDecorado.getTotal() + COSTO_EMPAQUE;
    }

    @Override
    public String getDescripcion() {
        return pedidoDecorado.getDescripcion() + " + Empaque de regalo (S/ 8.00)";
    }
}
