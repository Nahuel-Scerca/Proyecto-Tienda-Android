package com.example.proyecto_tienda_android.modelo;

public class UsuarioVentas {

    private Usuario usuario;
    private int cantidadVentas;

    public UsuarioVentas(Usuario usuario, int cantidadVentas) {
        this.usuario = usuario;
        this.cantidadVentas = cantidadVentas;
    }

    public UsuarioVentas() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(int cantidadVentas) {
        cantidadVentas = cantidadVentas;
    }
}
