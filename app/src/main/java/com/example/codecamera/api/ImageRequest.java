package com.example.codecamera.api;

public class ImageRequest {
    private Object data;

    public ImageRequest(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
