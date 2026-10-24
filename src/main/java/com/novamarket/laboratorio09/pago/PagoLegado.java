package com.novamarket.laboratorio09.pago;

/**
 * Proveedor heredado de pagos de NovaMarket.
 * Expone la operación ejecutarCargo(montoCentimos).
 * Esta clase simula un servicio externo — no se modifica.
 */
public class PagoLegado {

    /**
     * Ejecuta el cargo con el proveedor legado.
     *
     * @param montoCentimos monto en céntimos
     * @return true si el cargo fue exitoso
     */
    public boolean ejecutarCargo(int montoCentimos) {
        System.out.printf("[PagoLegado] Ejecutando cargo de %d céntimos (S/ %.2f)%n",
                montoCentimos, montoCentimos / 100.0);
        // Simulación: rechaza si el monto es 0 o negativo
        return montoCentimos > 0;
    }
}
