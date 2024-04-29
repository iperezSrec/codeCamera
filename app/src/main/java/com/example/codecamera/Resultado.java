package com.example.codecamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import androidx.appcompat.app.AppCompatActivity;

import com.example.codecamera.api.CommandRequest;
import com.example.codecamera.api.CommandService;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Resultado extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        mostrarResultado();
    }

    private void mostrarResultado() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String savedText1 = sharedPreferences.getString("text1", "");
        String savedText2 = sharedPreferences.getString("text2", "");
        String savedText3 = sharedPreferences.getString("text3", "");

        LinearLayout containerLayout = findViewById(R.id.containerLayout);

        if (savedText1.equals("Entrada") || savedText2.equals("Entrada") || savedText3.equals("Entrada")) {
            // Crear el botón "buttonEntrada"
            // Crear un RelativeLayout como contenedor para el ImageButton y el texto
            // Crear el botón "buttonEntrada"
            Button buttonEntrada = new Button(new ContextThemeWrapper(this, R.style.result_button));
            buttonEntrada.setText("Entrada");

            buttonEntrada.setBackgroundResource(R.drawable.entrada_button_background);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonEntrada.setTextAppearance(R.style.ResultButtonText);
            } else {
                buttonEntrada.setTextColor(getResources().getColor(R.color.sinvad));
                buttonEntrada.setTextSize(18); // Tamaño del texto en sp
                // Otros atributos de texto que quieras establecer
            }
            buttonEntrada.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Aquí colocas el código de la consulta

                        // Obtener la fecha y hora actual
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        String timestamp = sdf.format(new Date());

                        // Crear el objeto CommandRequest con los datos necesarios
                        CommandRequest request = new CommandRequest("entrada", "Test", timestamp);

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
                                    Toast.makeText(Resultado.this, "Solicitud POST exitosa", Toast.LENGTH_SHORT).show();
                                } else {
                                    // La solicitud POST falló
                                    Toast.makeText(Resultado.this, "Error en la solicitud POST", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                // Error en la comunicación con el servidor
                                Toast.makeText(Resultado.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
                            }
                        });

                Intent intent = new Intent(Resultado.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // Agregar el botón al contenedor (layout)
            containerLayout.addView(buttonEntrada);

            View spaceView = new View(this);
            spaceView.setLayoutParams(new LinearLayout.LayoutParams(
                    30, // Ajusta el ancho según tus necesidades
                    LinearLayout.LayoutParams.MATCH_PARENT));
            containerLayout.addView(spaceView);
        }

        if (savedText1.equals("Inicio") || savedText2.equals("Inicio") || savedText3.equals("Inicio")) {
            // Crear el botón "buttonInicio"
            Button buttonInicio = new Button(new ContextThemeWrapper(this, R.style.result_button));
            buttonInicio.setText("Inicio");

            buttonInicio.setBackgroundResource(R.drawable.inicio_button_background);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonInicio.setTextAppearance(R.style.ResultButtonText);
            } else {
                buttonInicio.setTextColor(getResources().getColor(R.color.sinvad));
                buttonInicio.setTextSize(18); // Tamaño del texto en sp
                // Otros atributos de texto que quieras establecer
            }

            buttonInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Aquí colocas el código de la consulta

                    // Obtener la fecha y hora actual
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String timestamp = sdf.format(new Date());

                    // Crear el objeto CommandRequest con los datos necesarios
                    CommandRequest request = new CommandRequest("inicio", "Test", timestamp);

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
                                Toast.makeText(Resultado.this, "Solicitud POST exitosa", Toast.LENGTH_SHORT).show();
                            } else {
                                // La solicitud POST falló
                                Toast.makeText(Resultado.this, "Error en la solicitud POST", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Error en la comunicación con el servidor
                            Toast.makeText(Resultado.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(Resultado.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // Agregar el botón al contenedor (layout)
            containerLayout.addView(buttonInicio);

            View spaceView = new View(this);
            spaceView.setLayoutParams(new LinearLayout.LayoutParams(
                    30, // Ajusta el ancho según tus necesidades
                    LinearLayout.LayoutParams.MATCH_PARENT));
            containerLayout.addView(spaceView);
        }

        if (savedText1.equals("Parada") || savedText2.equals("Parada") || savedText3.equals("Parada")) {
            // Crear el botón "buttonParada"
            Button buttonParada = new Button(new ContextThemeWrapper(this, R.style.result_button));
            buttonParada.setText("Parada");

            buttonParada.setBackgroundResource(R.drawable.parada_button_background);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonParada.setTextAppearance(R.style.ParadaButtonText);
            } else {
                buttonParada.setTextColor(getResources().getColor(R.color.sinvad));
                buttonParada.setTextSize(18); // Tamaño del texto en sp
                // Otros atributos de texto que quieras establecer
            }

            buttonParada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Aquí colocas el código de la consulta

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
                                Toast.makeText(Resultado.this, "Solicitud POST exitosa", Toast.LENGTH_SHORT).show();
                            } else {
                                // La solicitud POST falló
                                Toast.makeText(Resultado.this, "Error en la solicitud POST", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Error en la comunicación con el servidor
                            Toast.makeText(Resultado.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(Resultado.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // Agregar el botón al contenedor (layout)
            containerLayout.addView(buttonParada);
        }
    }
}


