package com.example.arbolito;

public class ZonaVerde {
    private int idzonaverde;
    private String nombre;
    private String direccion;
    private int iddistrito;
    private int status;
    private ZonaVerde data;
    private String message;

    public int getIdzonaverde() {
        return idzonaverde;
    }

    public void setIdzonaverde(int idzonaverde) {
        this.idzonaverde = idzonaverde;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIddistrito() {
        return iddistrito;
    }

    public void setIddistrito(int iddistrito) {
        this.iddistrito = iddistrito;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ZonaVerde getData() {
        return data;
    }

    public void setData(ZonaVerde data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
