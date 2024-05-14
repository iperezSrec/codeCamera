package com.example.codecamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
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


public class AccessControlActivity extends AppCompatActivity {

    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_control);

        messageTextView = findViewById(R.id.messageTextView);

        mostrarResultado();
    }

    private void deshabilitarBotones(Button buttonPresionado) {
        LinearLayout containerLayout = findViewById(R.id.containerLayout);
        for (int i = 0; i < containerLayout.getChildCount(); i++) {
            View child = containerLayout.getChildAt(i);
            if (child instanceof Button && child != buttonPresionado) {
                child.setEnabled(false);
            }
        }
    }

    private void mostrarResultado() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String savedText1 = sharedPreferences.getString("text1", "");
        String savedText2 = sharedPreferences.getString("text2", "");
        String savedText3 = sharedPreferences.getString("text3", "");

        LinearLayout containerLayout = findViewById(R.id.containerLayout);

        if (!savedText1.isEmpty()) {

            Button buttonEntrada = new Button(new ContextThemeWrapper(this, R.style.result_button));
            buttonEntrada.setText(savedText1);

            buttonEntrada.setBackgroundResource(R.drawable.entrada_button_background);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonEntrada.setTextAppearance(R.style.ResultButtonText);
            } else {
                buttonEntrada.setTextColor(getResources().getColor(R.color.colorPrimario));
                buttonEntrada.setTextSize(18);
            }
            buttonEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonEntrada.setEnabled(false);

                    SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    String phId = sharedPreferences.getString("phId", "");
                    String location = sharedPreferences.getString("location", "");

                    if (!phId.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        String timestamp = sdf.format(new Date());

                        CommandRequest request = new CommandRequest("entrada", location, timestamp);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://client.pro.srec.solutions/v1/oasis/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CommandService service = retrofit.create(CommandService.class);

                        Call<Void> call = service.executeCommand(phId, request);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response);
                                if (response.isSuccessful()) {
                                    messageTextView.setText("Entrada Realizada!");

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(AccessControlActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 3000);
                                } else {
                                    Toast.makeText(AccessControlActivity.this, "Error en la solicitud POST", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(AccessControlActivity.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(AccessControlActivity.this, "No se ha encontrado un phId válido en SharedPreferences", Toast.LENGTH_SHORT).show();
                    }
                    deshabilitarBotones(buttonEntrada);
                }
            });


            containerLayout.addView(buttonEntrada);

            View spaceView = new View(this);
            spaceView.setLayoutParams(new LinearLayout.LayoutParams(
                    30,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            containerLayout.addView(spaceView);
        }

        if (!savedText2.isEmpty()) {
            Button buttonInicio = new Button(new ContextThemeWrapper(this, R.style.result_button));
            buttonInicio.setText(savedText2);

            buttonInicio.setBackgroundResource(R.drawable.inicio_button_background);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonInicio.setTextAppearance(R.style.ResultButtonText);
            } else {
                buttonInicio.setTextColor(getResources().getColor(R.color.colorPrimario));
                buttonInicio.setTextSize(18);
            }

            buttonInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonInicio.setEnabled(false);

                    SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    String phId = sharedPreferences.getString("phId", "");
                    String location = sharedPreferences.getString("location", "");

                    if (!phId.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        String timestamp = sdf.format(new Date());

                        CommandRequest request = new CommandRequest("inicio", location, timestamp);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://client.pro.srec.solutions/v1/oasis/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CommandService service = retrofit.create(CommandService.class);
                        Call<Void> call = service.executeCommand(phId, request);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response);
                                if (response.isSuccessful()) {
                                    messageTextView.setText("Inicio Realizado!");

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(AccessControlActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 3000);
                                } else {
                                    Toast.makeText(AccessControlActivity.this, "Error en la solicitud POST", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(AccessControlActivity.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(AccessControlActivity.this, "No se ha encontrado un phId válido en SharedPreferences", Toast.LENGTH_SHORT).show();
                    }
                    deshabilitarBotones(buttonInicio);
                }
            });

            containerLayout.addView(buttonInicio);

            View spaceView = new View(this);
            spaceView.setLayoutParams(new LinearLayout.LayoutParams(
                    30,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            containerLayout.addView(spaceView);
        }

        if (!savedText3.isEmpty()) {
            Button buttonParada = new Button(new ContextThemeWrapper(this, R.style.result_button));
            buttonParada.setText(savedText3);

            buttonParada.setBackgroundResource(R.drawable.parada_button_background);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonParada.setTextAppearance(R.style.ParadaButtonText);
            } else {
                buttonParada.setTextColor(getResources().getColor(R.color.colorPrimario));
                buttonParada.setTextSize(18);
            }

            buttonParada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonParada.setEnabled(false);

                    SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    String phId = sharedPreferences.getString("phId", "");
                    String location = sharedPreferences.getString("location", "");

                    if (!phId.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        String timestamp = sdf.format(new Date());

                        CommandRequest request = new CommandRequest("parada", location, timestamp);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://client.pro.srec.solutions/v1/oasis/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CommandService service = retrofit.create(CommandService.class);

                        Call<Void> call = service.executeCommand(phId, request);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response);
                                if (response.isSuccessful()) {
                                    messageTextView.setText("Parada Realizada!");

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(AccessControlActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 3000);
                                } else {
                                    Toast.makeText(AccessControlActivity.this, "Error en la solicitud POST", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(AccessControlActivity.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(AccessControlActivity.this, "No se ha encontrado un phId válido en SharedPreferences", Toast.LENGTH_SHORT).show();
                    }
                    deshabilitarBotones(buttonParada);
                }
            });
            containerLayout.addView(buttonParada);
        }
    }
}


