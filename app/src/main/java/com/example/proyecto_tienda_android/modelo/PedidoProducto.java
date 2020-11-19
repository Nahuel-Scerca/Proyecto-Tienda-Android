package com.example.proyecto_tienda_android.modelo;

import java.io.Serializable;

public class PedidoProducto implements Serializable {

    private int id;
    private double precioUnidad;
    private int cantidad;
    private double montoTotal;
    private Producto producto;
    private int productoId;
    private Pedido pedido;
    private int pedidoId;

    public PedidoProducto(int id, double precioUnidad, int cantidad, double montoTotal, Producto producto, int productoId, Pedido pedido, int pedidoId) {
        this.id = id;
        this.precioUnidad = precioUnidad;
        this.cantidad = cantidad;
        this.montoTotal = montoTotal;
        this.producto = producto;
        this.productoId = productoId;
        this.pedido = pedido;
        this.pedidoId = pedidoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }
}
