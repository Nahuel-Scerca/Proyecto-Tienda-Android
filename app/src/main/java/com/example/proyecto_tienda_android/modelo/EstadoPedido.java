package com.example.proyecto_tienda_android.modelo;

import java.io.Serializable;
import java.util.Date;

public class EstadoPedido implements Serializable {

    private int id;
    private String nombreEstado;
    private Date fechaSalida;
    private Date fechaLlegada;

    public EstadoPedido(int id, String nombreEstado, Date fechaSalida, Date fechaLlegada) {
        this.id = id;
        this.nombreEstado = nombreEstado;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
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
}
