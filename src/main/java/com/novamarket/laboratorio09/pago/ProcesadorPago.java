package com.novamarket.laboratorio09.pago;

/**
 * Interfaz uniforme que usa el sistema de pedidos para procesar pagos.
 * (RF-02) La lógica de pedidos no depende de los nombres internos de
 * cada proveedor externo — solo conoce esta interfaz.
 */
public interface ProcesadorPago {

    /**
     * Procesa el pago del importe indicado.
     *
     * @param montoCentimos monto en céntimos enteros (ej. S/100.00 → 10000)
     * @return true si el pago fue autorizado, false si fue rechazado
     */
    boolean procesarPago(int montoCentimos);

    /** Nombre descriptivo del proveedor (para logs y UI). */
    String getNombreProveedor();
}
