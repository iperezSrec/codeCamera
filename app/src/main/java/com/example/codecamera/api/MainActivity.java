package com.example.codecamera.api;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codecamera.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener la fecha y hora actual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String timestamp = sdf.format(new Date());

        // Crear el objeto CommandRequest con los datos necesarios
        CommandRequest request = new CommandRequest("parada", "Test", timestamp);

        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://client.pro.srec.solutions/v1/oasis/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Obtener la instancia del servicio CommandService
        CommandService service = retrofit.create(CommandService.class);

        // Realizar la solicitud POST
        Call<Void> call = service.executeCommand(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response);
                if (response.isSuccessful()) {
                    // La solicitud POST fue exitosa
                    Toast.makeText(MainActivity.this, "Solicitud POST exitosa", Toast.LENGTH_SHORT).show();
                } else {
                    // La solicitud POST falló
                    Toast.makeText(MainActivity.this, "Error en la solicitud POST", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Error en la comunicación con el servidor
                Toast.makeText(MainActivity.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
