package com.example.proyecto_tienda_android.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable {

    private int id;
    //private List<PedidoProducto> listaLineas;
    private Date fecha;
    private Date fechaSalida;
    private Date fechaLlegada;
    private double precioFinal;
    private Cliente cliente;
    private int clienteId;
    public String estadoNombre;
    public int estado;
    public String usuarioACargo;
    public int asingnado;


    public Pedido(int id, Date fecha, Date fechaSalida, Date fechaLlegada, double precioFinal, Cliente cliente, int clienteId, String estadoNombre, int estado, String usuarioACargo, int asingnado) {
        this.id = id;
        this.fecha = fecha;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.precioFinal = precioFinal;
        this.cliente = cliente;
        this.clienteId = clienteId;
        this.estadoNombre = estadoNombre;
        this.estado = estado;
        this.usuarioACargo = usuarioACargo;
        this.asingnado = asingnado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getUsuarioACargo() {
        return usuarioACargo;
    }

    public void setUsuarioACargo(String usuarioACargo) {
        this.usuarioACargo = usuarioACargo;
    }

    public int getAsingnado() {
        return asingnado;
    }

    public void setAsingnado(int asingnado) {
        this.asingnado = asingnado;
    }
}
