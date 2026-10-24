package com.novamarket.laboratorio09.pago;

/**
 * PATRÓN ADAPTER — RF-02, RF-03
 *
 * Adapta PagoLegado (que tiene ejecutarCargo) a la interfaz
 * ProcesadorPago (que espera procesarPago).
 *
 * El sistema de pedidos solo conoce ProcesadorPago; nunca
 * llama directamente a PagoLegado.
 */
public class AdaptadorPagoLegado implements ProcesadorPago {

    private final PagoLegado pagoLegado;

    public AdaptadorPagoLegado(PagoLegado pagoLegado) {
        this.pagoLegado = pagoLegado;
    }

    @Override
    public boolean procesarPago(int montoCentimos) {
        // Traduce la llamada: procesarPago → ejecutarCargo
        return pagoLegado.ejecutarCargo(montoCentimos);
    }

    @Override
    public String getNombreProveedor() {
        return "PagoLegado (vía Adapter)";
    }
}
