package com.example.arbolito;

public class ResponseDistrito {
    private int status;
    private String message;
    private Distrito[] data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Distrito[] getData() {
        return data;
    }

    public void setData(Distrito[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
