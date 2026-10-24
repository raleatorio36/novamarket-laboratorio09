package com.novamarket.laboratorio09.pago;

/**
 * Proveedor moderno de pagos de NovaMarket.
 * Expone la operación autorizarPago(montoCentimos).
 * Esta clase simula un servicio externo — no se modifica.
 */
public class PagoExpress {

    /**
     * Autoriza el pago con el proveedor moderno.
     *
     * @param montoCentimos monto en céntimos
     * @return true si la autorización fue exitosa
     */
    public boolean autorizarPago(int montoCentimos) {
        System.out.printf("[PagoExpress] Autorizando pago de %d céntimos (S/ %.2f)%n",
                montoCentimos, montoCentimos / 100.0);
        // Simulación: rechaza si el monto es 0 o negativo
        return montoCentimos > 0;
    }
}
