package com.example.arbolito;

public class ResponseArbol {
    private int status;
    private Arbol[] data;
    private String message;

    private int idarbol;
    private String nombre;
    private String tipo;
    private String dirfoto;
    private int idpersona;
    private int idzonaverde;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Arbol[] getData() {
        return data;
    }

    public void setData(Arbol[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdarbol() {
        return idarbol;
    }

    public void setIdarbol(int idarbol) {
        this.idarbol = idarbol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDirfoto() {
        return dirfoto;
    }

    public void setDirfoto(String dirfoto) {
        this.dirfoto = dirfoto;
    }

    public int getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(int idpersona) {
        this.idpersona = idpersona;
    }

    public int getIdzonaverde() {
        return idzonaverde;
    }

    public void setIdzonaverde(int idzonaverde) {
        this.idzonaverde = idzonaverde;
    }
}
