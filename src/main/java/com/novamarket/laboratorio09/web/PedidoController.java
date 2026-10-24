package com.novamarket.laboratorio09.web;

import com.novamarket.laboratorio09.fachada.FachadaPedidos;
import com.novamarket.laboratorio09.fachada.ResultadoConfirmacion;
import com.novamarket.laboratorio09.modelo.ItemPedido;
import com.novamarket.laboratorio09.modelo.Pedido;
import com.novamarket.laboratorio09.notificacion.NotificacionConAuditoria;
import com.novamarket.laboratorio09.notificacion.ServicioNotificacion;
import com.novamarket.laboratorio09.pago.AdaptadorPagoExpress;
import com.novamarket.laboratorio09.pago.AdaptadorPagoLegado;
import com.novamarket.laboratorio09.pago.PagoExpress;
import com.novamarket.laboratorio09.pago.PagoLegado;
import com.novamarket.laboratorio09.pago.ProcesadorPago;
import com.novamarket.laboratorio09.pedido.DecoradorEmpaqueRegalo;
import com.novamarket.laboratorio09.pedido.DecoradorPrioridad;
import com.novamarket.laboratorio09.pedido.PedidoBase;
import com.novamarket.laboratorio09.pedido.PedidoServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador web de NovaMarket.
 * Permite demostrar los casos de prueba CP-01 a CP-09 desde el navegador.
 */
@Controller
public class PedidoController {

    private final FachadaPedidos fachadaPedidos;
    private final ServicioNotificacion servicioNotificacion;

    public PedidoController(FachadaPedidos fachadaPedidos,
                             ServicioNotificacion servicioNotificacion) {
        this.fachadaPedidos = fachadaPedidos;
        this.servicioNotificacion = servicioNotificacion;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("titulo", "NovaMarket — Laboratorio 09");
        return "index";
    }

    /**
     * Confirma un pedido con opciones configurables desde la UI.
     *
     * @param empaque    true = agregar empaque de regalo (+ S/ 8.00)
     * @param prioridad  true = agregar prioridad (+ S/ 12.00)
     * @param proveedor  "legado" o "express"
     * @param stockCero  true = simular inventario vacío (CP-07)
     * @param pagoFalla  true = simular pago rechazado (CP-06)
     */
    @GetMapping("/confirmar")
    public String confirmarPedido(
            @RequestParam(defaultValue = "false") boolean empaque,
            @RequestParam(defaultValue = "false") boolean prioridad,
            @RequestParam(defaultValue = "legado") String proveedor,
            @RequestParam(defaultValue = "false") boolean stockCero,
            @RequestParam(defaultValue = "false") boolean pagoFalla,
            Model model) {

        // Construir el pedido base del escenario de la guía
        Pedido pedido = new Pedido("NV-1001");
        pedido.agregarItem(new ItemPedido("NV-1001", "Mouse inalámbrico", 1, 45.00));
        pedido.agregarItem(new ItemPedido("NV-1001", "Audífonos USB", 1, 55.00));

        // Aplicar decoradores según las opciones seleccionadas (Módulo 2)
        PedidoServicio pedidoServicio = new PedidoBase(pedido);
        if (empaque) pedidoServicio = new DecoradorEmpaqueRegalo(pedidoServicio);
        if (prioridad) pedidoServicio = new DecoradorPrioridad(pedidoServicio);

        // Seleccionar proveedor de pago (Módulo 1 — Adapter)
        ProcesadorPago procesador = seleccionarProcesador(proveedor, pagoFalla);

        // Crear fachada con el procesador elegido y la notificación con auditoría
        FachadaPedidos fachada = new FachadaPedidos(servicioNotificacion, procesador);

        // Simular falta de stock para CP-07
        if (stockCero) {
            fachada.ajustarStock("NV-1001", 0);
        }

        // Ejecutar confirmación (Módulo 3 — Facade)
        ResultadoConfirmacion resultado = fachada.confirmarPedido(pedidoServicio, pedido);

        // Obtener log de auditoría si aplica (Módulo 4)
        List<String> auditoria = new ArrayList<>();
        if (servicioNotificacion instanceof NotificacionConAuditoria nca) {
            auditoria = nca.getRegistrosAuditoria();
        }

        model.addAttribute("resultado", resultado);
        model.addAttribute("pedido", pedido);
        model.addAttribute("pedidoServicio", pedidoServicio);
        model.addAttribute("proveedorUsado", procesador.getNombreProveedor());
        model.addAttribute("auditoria", auditoria);

        return "resultado";
    }

    private ProcesadorPago seleccionarProcesador(String proveedor, boolean pagoFalla) {
        if ("express".equalsIgnoreCase(proveedor)) {
            if (pagoFalla) {
                // Proveedor express que siempre rechaza
                return new ProcesadorPago() {
                    @Override public boolean procesarPago(int m) { return false; }
                    @Override public String getNombreProveedor() { return "PagoExpress (simulando rechazo)"; }
                };
            }
            return new AdaptadorPagoExpress(new PagoExpress());
        }
        if (pagoFalla) {
            // Proveedor legado que siempre rechaza
            return new ProcesadorPago() {
                @Override public boolean procesarPago(int m) { return false; }
                @Override public String getNombreProveedor() { return "PagoLegado (simulando rechazo)"; }
            };
        }
        return new AdaptadorPagoLegado(new PagoLegado());
    }
}
