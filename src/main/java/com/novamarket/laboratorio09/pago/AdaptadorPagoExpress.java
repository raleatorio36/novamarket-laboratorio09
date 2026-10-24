package com.novamarket.laboratorio09.pago;

/**
 * PATRÓN ADAPTER — RF-02, RF-03
 *
 * Adapta PagoExpress (que tiene autorizarPago) a la interfaz
 * ProcesadorPago (que espera procesarPago).
 *
 * El sistema de pedidos solo conoce ProcesadorPago; nunca
 * llama directamente a PagoExpress.
 */
public class AdaptadorPagoExpress implements ProcesadorPago {

    private final PagoExpress pagoExpress;

    public AdaptadorPagoExpress(PagoExpress pagoExpress) {
        this.pagoExpress = pagoExpress;
    }

    @Override
    public boolean procesarPago(int montoCentimos) {
        // Traduce la llamada: procesarPago → autorizarPago
        return pagoExpress.autorizarPago(montoCentimos);
    }

    @Override
    public String getNombreProveedor() {
        return "PagoExpress (vía Adapter)";
    }
}
