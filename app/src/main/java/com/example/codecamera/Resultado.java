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

import androidx.appcompat.app.AppCompatActivity;

import com.example.codecamera.api.ApiUser;
import com.example.codecamera.api.FicharRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Resultado extends AppCompatActivity {


    // Crear una instancia de Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://client.pre.srec.solutions/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Crear una instancia de ApiUser utilizando Retrofit
    ApiUser apiUser = retrofit.create(ApiUser.class);


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
                    // Iniciar MainActivity al hacer clic en el botón "Desfichar"
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

        if (savedText1.equals("Fichar") || savedText2.equals("Fichar") || savedText3.equals("Fichar")) {
            // Crear el botón "buttonFichar"
            Button buttonFichar = new Button(new ContextThemeWrapper(this, R.style.result_button));
            buttonFichar.setText("Fichar");

            buttonFichar.setBackgroundResource(R.drawable.fichar_button_background);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonFichar.setTextAppearance(R.style.ResultButtonText);
            } else {
                buttonFichar.setTextColor(getResources().getColor(R.color.sinvad));
                buttonFichar.setTextSize(18); // Tamaño del texto en sp
                // Otros atributos de texto que quieras establecer
            }

            buttonFichar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtener el phyId de las preferencias compartidas
                    SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    String phyId = sharedPreferences.getString("phyId", "");

                        // Crear una instancia de FicharRequest con los datos adecuados
                        FicharRequest request = new FicharRequest("parada", "Test", "2024-03-26T13:37:21");

                        // Llamar al método fichar de la interfaz ApiUser
                        Call<Void> call = apiUser.fichar("b2e1a685386a44ca9f68-2c05057103f", phyId, request);

                        // Realizar la llamada asíncrona
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(Resultado.this, "¡Fichaje exitoso!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Resultado.this, "Error en el servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(Resultado.this, "Error al enviar la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    Intent intent = new Intent(Resultado.this, MainActivity.class);
                    startActivity(intent);
                }
            });




            // Agregar el botón al contenedor (layout)
            containerLayout.addView(buttonFichar);

            View spaceView = new View(this);
            spaceView.setLayoutParams(new LinearLayout.LayoutParams(
                    30, // Ajusta el ancho según tus necesidades
                    LinearLayout.LayoutParams.MATCH_PARENT));
            containerLayout.addView(spaceView);
        }

        if (savedText1.equals("Desfichar") || savedText2.equals("Desfichar") || savedText3.equals("Desfichar")) {
            // Crear el botón "buttonDesfichar"
            Button buttonDesfichar = new Button(new ContextThemeWrapper(this, R.style.result_button));
            buttonDesfichar.setText("Parada");

            buttonDesfichar.setBackgroundResource(R.drawable.desfichar_button_background);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonDesfichar.setTextAppearance(R.style.DesfichajeButtonText);
            } else {
                buttonDesfichar.setTextColor(getResources().getColor(R.color.sinvad));
                buttonDesfichar.setTextSize(18); // Tamaño del texto en sp
                // Otros atributos de texto que quieras establecer
            }

            buttonDesfichar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Iniciar MainActivity al hacer clic en el botón "Desfichar"
                    Intent intent = new Intent(Resultado.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // Agregar el botón al contenedor (layout)
            containerLayout.addView(buttonDesfichar);
        }
    }
}


