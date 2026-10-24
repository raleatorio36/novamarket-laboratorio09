package com.novamarket.laboratorio09.notificacion;

import org.springframework.stereotype.Component;

/**
 * Implementación base del servicio de notificaciones.
 * Simula el envío de un mensaje al cliente (por consola).
 */
@Component
public class NotificacionBasica implements ServicioNotificacion {

    @Override
    public void notificar(String codigoPedido, String mensaje) {
        System.out.printf("[NOTIFICACIÓN] Pedido %s: %s%n", codigoPedido, mensaje);
    }
}
