package com.example.codecamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class notiContra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_contra);
    }

    public void onYesClicked(View view) {
        // Aquí puedes implementar la lógica para notificar al servidor
        // Por ejemplo:
        // Toast.makeText(this, "Notificación enviada", Toast.LENGTH_SHORT).show();

        // Puedes agregar aquí código adicional para notificar al servidor

        // Cierra esta actividad y vuelve a la actividad principal
        finish();
    }

    public void onNoClicked(View view) {
        // Cierra esta actividad y vuelve a la actividad principal sin hacer nada adicional
        finish();
    }
}
