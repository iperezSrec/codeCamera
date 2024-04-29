package com.example.codecamera.api;

import android.util.Log;

public class ImageRequest {
    private String name;
    private Object data;

    public ImageRequest(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean sendImageToServer() {
        // Aquí implementa la lógica para enviar la imagen en formato Base64 al servidor
        // Puedes utilizar Retrofit u otra librería para realizar la solicitud PUT al servidor
        // Recuerda incluir la lógica para manejar la respuesta del servidor si es necesario

        // Supongamos que la imagen se envió correctamente
        boolean imageSentSuccessfully = true;

        Log.d("ImageRequest", "Enviando imagen al servidor. Nombre: " + name + ", Datos: " + data);

        return imageSentSuccessfully;
    }
}
