package com.novamarket.laboratorio09.config;

import com.novamarket.laboratorio09.notificacion.NotificacionBasica;
import com.novamarket.laboratorio09.notificacion.NotificacionConAuditoria;
import com.novamarket.laboratorio09.notificacion.ServicioNotificacion;
import com.novamarket.laboratorio09.pago.AdaptadorPagoLegado;
import com.novamarket.laboratorio09.pago.PagoLegado;
import com.novamarket.laboratorio09.pago.ProcesadorPago;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuración de los beans de NovaMarket.
 * Aquí se decide qué implementaciones concretas usa la aplicación,
 * sin que el resto del código necesite conocer los detalles.
 */
@Configuration
public class NovaMarketConfig {

    /**
     * Proveedor de pago activo: PagoLegado adaptado.
     * Para cambiar a PagoExpress, basta con intercambiar este bean.
     */
    @Bean
    @Primary
    public ProcesadorPago procesadorPago() {
        return new AdaptadorPagoLegado(new PagoLegado());
    }

    /**
     * Servicio de notificaciones con auditoría activa.
     * El Decorator envuelve la NotificacionBasica sin modificarla.
     */
    @Bean
    @Primary
    public ServicioNotificacion servicioNotificacion(NotificacionBasica notificacionBasica) {
        return new NotificacionConAuditoria(notificacionBasica);
    }
}
