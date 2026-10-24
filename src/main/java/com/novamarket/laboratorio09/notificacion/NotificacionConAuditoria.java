package com.novamarket.laboratorio09.notificacion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PATRÓN DECORATOR — RF-06
 *
 * Extiende cualquier ServicioNotificacion agregando un registro
 * de auditoría sin modificar el componente original.
 * El administrador puede consultar el log de auditoría.
 */
public class NotificacionConAuditoria implements ServicioNotificacion {

    private final ServicioNotificacion notificacionDecorada;
    private final List<String> registrosAuditoria = new ArrayList<>();
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public NotificacionConAuditoria(ServicioNotificacion notificacionDecorada) {
        this.notificacionDecorada = notificacionDecorada;
    }

    @Override
    public void notificar(String codigoPedido, String mensaje) {
        // 1. Delega al servicio original
        notificacionDecorada.notificar(codigoPedido, mensaje);

        // 2. Agrega el registro de auditoría sin tocar el original
        String registro = String.format("[AUDITORÍA %s] Pedido: %s | Msg: %s",
                LocalDateTime.now().format(FORMATO), codigoPedido, mensaje);
        registrosAuditoria.add(registro);
        System.out.println(registro);
    }

    /** Retorna el log de auditoría (para el administrador). */
    public List<String> getRegistrosAuditoria() {
        return Collections.unmodifiableList(registrosAuditoria);
    }
}
