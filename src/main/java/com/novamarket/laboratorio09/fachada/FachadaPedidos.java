package com.novamarket.laboratorio09.fachada;

import com.novamarket.laboratorio09.modelo.EstadoPedido;
import com.novamarket.laboratorio09.notificacion.ServicioNotificacion;
import com.novamarket.laboratorio09.pago.ProcesadorPago;
import com.novamarket.laboratorio09.pedido.PedidoServicio;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * PATRÓN FACADE — RF-04, RF-07, RF-09
 *
 * Expone una única operación de alto nivel para confirmar pedidos.
 * El colaborador de ventas (o el controller web) solo necesita
 * llamar a confirmarPedido(); la fachada coordina internamente:
 *   1. Verificar disponibilidad de stock
 *   2. Calcular el importe total (con servicios opcionales)
 *   3. Procesar el pago
 *   4. Cambiar el estado del pedido
 *   5. Enviar la notificación
 *
 * Si algún paso falla, el pedido queda RECHAZADO y se informa el motivo.
 */
@Service
public class FachadaPedidos {

    private final ServicioNotificacion servicioNotificacion;
    private final ProcesadorPago procesadorPago;

    /**
     * Inventario simulado: código de producto → unidades disponibles.
     * RF-07: permite simular falta de stock para CP-07.
     */
    private final Map<String, Integer> inventario = new HashMap<>();

    public FachadaPedidos(ServicioNotificacion servicioNotificacion,
                          ProcesadorPago procesadorPago) {
        this.servicioNotificacion = servicioNotificacion;
        this.procesadorPago = procesadorPago;
        inicializarInventario();
    }

    private void inicializarInventario() {
        // Stock inicial del escenario
        inventario.put("NV-1001", 10);
    }

    /**
     * Ajusta el stock de un producto (útil para simular CP-07).
     *
     * @param codigoProducto código del producto
     * @param cantidad       nuevo stock disponible
     */
    public void ajustarStock(String codigoProducto, int cantidad) {
        inventario.put(codigoProducto, cantidad);
    }

    /**
     * Operación principal de confirmación.
     *
     * @param pedidoServicio pedido (puede ser base o decorado con servicios opcionales)
     * @param pedidoModelo   modelo del pedido (para cambiar el estado)
     * @return resultado indicando éxito o motivo de fallo
     */
    public ResultadoConfirmacion confirmarPedido(
            PedidoServicio pedidoServicio,
            com.novamarket.laboratorio09.modelo.Pedido pedidoModelo) {

        String codigo = pedidoServicio.getCodigo();

        // Paso 1 — Verificar disponibilidad de stock (RF-07)
        for (var item : pedidoModelo.getItemsInmutables()) {
            int stockDisponible = inventario.getOrDefault(item.getCodigo(), 0);
            if (stockDisponible < item.getCantidad()) {
                pedidoModelo.setEstado(EstadoPedido.RECHAZADO);
                String motivo = String.format(
                        "Stock insuficiente para '%s': disponible %d, solicitado %d.",
                        item.getNombre(), stockDisponible, item.getCantidad());
                servicioNotificacion.notificar(codigo, "RECHAZADO — " + motivo);
                return ResultadoConfirmacion.fallo(motivo);
            }
            // Descontar del inventario simulado
            inventario.put(item.getCodigo(), stockDisponible - item.getCantidad());
        }

        // Paso 2 — Calcular importe total (incluye servicios opcionales del Decorator)
        double total = pedidoServicio.getTotal();
        int centimos = (int) Math.round(total * 100);

        // Paso 3 — Procesar pago (RF-02, RF-03)
        boolean pagoCorrecto = procesadorPago.procesarPago(centimos);
        if (!pagoCorrecto) {
            pedidoModelo.setEstado(EstadoPedido.RECHAZADO);
            String motivo = String.format(
                    "Pago rechazado por %s para el importe S/ %.2f.",
                    procesadorPago.getNombreProveedor(), total);
            servicioNotificacion.notificar(codigo, "RECHAZADO — " + motivo);
            return ResultadoConfirmacion.fallo(motivo);
        }

        // Paso 4 — Confirmar estado del pedido
        pedidoModelo.setEstado(EstadoPedido.CONFIRMADO);

        // Paso 5 — Notificar al cliente (RF-06)
        servicioNotificacion.notificar(codigo,
                String.format("Confirmado. Total cobrado: S/ %.2f via %s.",
                        total, procesadorPago.getNombreProveedor()));

        return ResultadoConfirmacion.exito(total);
    }
}
