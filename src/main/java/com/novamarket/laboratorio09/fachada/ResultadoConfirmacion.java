package com.novamarket.laboratorio09.fachada;

import lombok.Getter;

/**
 * Resultado de la operación de confirmación de un pedido.
 * Encapsula si fue exitosa, el mensaje y el total cobrado.
 */
@Getter
public class ResultadoConfirmacion {

    private final boolean exitoso;
    private final String mensaje;
    private final double totalCobrado;

    public ResultadoConfirmacion(boolean exitoso, String mensaje, double totalCobrado) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.totalCobrado = totalCobrado;
    }

    public static ResultadoConfirmacion exito(double total) {
        return new ResultadoConfirmacion(true,
                String.format("Pedido confirmado correctamente. Total cobrado: S/ %.2f", total),
                total);
    }

    public static ResultadoConfirmacion fallo(String motivo) {
        return new ResultadoConfirmacion(false, motivo, 0.0);
    }
}
