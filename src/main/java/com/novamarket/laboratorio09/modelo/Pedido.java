package com.novamarket.laboratorio09.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidad principal que representa un pedido de NovaMarket.
 * Contiene los ítems, el total base y el estado actual.
 */
@Getter
public class Pedido {

    private final String codigo;
    private final List<ItemPedido> items;

    @Setter
    private EstadoPedido estado;

    public Pedido(String codigo) {
        this.codigo = codigo;
        this.items = new ArrayList<>();
        this.estado = EstadoPedido.PENDIENTE;
    }

    public void agregarItem(ItemPedido item) {
        items.add(item);
    }

    /** Total base sin servicios opcionales. */
    public double getTotal() {
        return items.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }

    /** Total en céntimos enteros para el procesador de pagos (evita redondeo). */
    public int getTotalEnCentimos() {
        return (int) Math.round(getTotal() * 100);
    }

    public List<ItemPedido> getItemsInmutables() {
        return Collections.unmodifiableList(items);
    }
}
