package com.novamarket.laboratorio09.notificacion;

/**
 * Contrato para el envío de notificaciones.
 * (RF-06) Permite extender con auditoría sin reescribir el componente original.
 */
public interface ServicioNotificacion {

    /**
     * Envía una notificación sobre el pedido indicado.
     *
     * @param codigoPedido código del pedido
     * @param mensaje      mensaje a comunicar
     */
    void notificar(String codigoPedido, String mensaje);
}
