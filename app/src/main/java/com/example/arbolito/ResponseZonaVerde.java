package com.example.arbolito;

public class ResponseZonaVerde {
    private int status;
    private ZonaVerde[] data;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ZonaVerde[] getData() {
        return data;
    }

    public void setData(ZonaVerde[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
