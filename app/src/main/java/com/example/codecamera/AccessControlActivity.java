package com.example.codecamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.codecamera.api.CommandRequest;
import com.example.codecamera.api.CommandService;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        int imageResId1 = sharedPreferences.getInt("image1", 0);
        int imageResId2 = sharedPreferences.getInt("image2", 0);
        int imageResId3 = sharedPreferences.getInt("image3", 0);

        LinearLayout containerLayout = findViewById(R.id.containerLayout);

        if (!savedText1.isEmpty() && imageResId1 != 0) {
            Button buttonEntrada = new Button(new ContextThemeWrapper(this, R.style.CustomButtonStyle));
            buttonEntrada.setText(savedText1);
            buttonEntrada.setBackground(null);

            GradientDrawable border = new GradientDrawable();
            border.setColor(Color.TRANSPARENT);
            border.setStroke(10, getResources().getColor(R.color.colorPrimario));
            border.setCornerRadius(8);
            buttonEntrada.setBackground(border);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonEntrada.setTextAppearance(R.style.ResultButtonText);
            } else {
                buttonEntrada.setTextColor(getResources().getColor(R.color.colorPrimario));
                buttonEntrada.setTextSize(30);
            }

            Drawable drawable1 = ContextCompat.getDrawable(this, imageResId1);
            if (drawable1 != null) {
                int colorPrimario = ContextCompat.getColor(this, R.color.colorPrimario);
                drawable1.setTint(colorPrimario);
                buttonEntrada.setCompoundDrawablesWithIntrinsicBounds(null, drawable1, null, null);
                buttonEntrada.setCompoundDrawablePadding(0);
            }

            buttonEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleButtonClick(buttonEntrada, savedText1);
                }
            });

            containerLayout.addView(buttonEntrada);
            addSpaceView(containerLayout);
        }

        if (!savedText2.isEmpty() && imageResId2 != 0) {
            Button buttonInicio = new Button(new ContextThemeWrapper(this, R.style.CustomButtonStyle));
            buttonInicio.setText(savedText2);
            buttonInicio.setBackground(null);

            GradientDrawable border = new GradientDrawable();
            border.setColor(Color.TRANSPARENT);
            border.setStroke(10, getResources().getColor(R.color.colorPrimario));
            border.setCornerRadius(8);
            buttonInicio.setBackground(border);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonInicio.setTextAppearance(R.style.ResultButtonText);
            } else {
                buttonInicio.setTextColor(getResources().getColor(R.color.colorPrimario));
                buttonInicio.setTextSize(30);
            }

            Drawable drawable2 = ContextCompat.getDrawable(this, imageResId2);
            if (drawable2 != null) {
                int colorPrimario = ContextCompat.getColor(this, R.color.colorPrimario);
                drawable2.setTint(colorPrimario);
                buttonInicio.setCompoundDrawablesWithIntrinsicBounds(null, drawable2, null, null);
                buttonInicio.setCompoundDrawablePadding(0);
            }

            buttonInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleButtonClick(buttonInicio, savedText2);
                }
            });

            containerLayout.addView(buttonInicio);
            addSpaceView(containerLayout);
        }

        if (!savedText3.isEmpty() && imageResId3 != 0) {
            Button buttonParada = new Button(new ContextThemeWrapper(this, R.style.CustomButtonStyle));
            buttonParada.setText(savedText3);
            buttonParada.setBackground(null);

            GradientDrawable border = new GradientDrawable();
            border.setColor(Color.TRANSPARENT);
            border.setStroke(10, getResources().getColor(R.color.colorPrimario));
            border.setCornerRadius(8);
            buttonParada.setBackground(border);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonParada.setTextAppearance(R.style.ResultButtonText);
            } else {
                buttonParada.setTextColor(getResources().getColor(R.color.colorPrimario));
                buttonParada.setTextSize(30);
            }

            Drawable drawable3 = ContextCompat.getDrawable(this, imageResId3);
            if (drawable3 != null) {
                int colorPrimario = ContextCompat.getColor(this, R.color.colorPrimario);
                drawable3.setTint(colorPrimario);
                buttonParada.setCompoundDrawablesWithIntrinsicBounds(null, drawable3, null, null);
                buttonParada.setCompoundDrawablePadding(0);
            }

            buttonParada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleButtonClick(buttonParada, savedText3);
                }
            });

            containerLayout.addView(buttonParada);
        }
    }

    private void addSpaceView(LinearLayout containerLayout) {
        View spaceView = new View(this);
        spaceView.setLayoutParams(new LinearLayout.LayoutParams(
                30,
                LinearLayout.LayoutParams.MATCH_PARENT));
        containerLayout.addView(spaceView);
    }
    private void handleButtonClick(Button button, String text) {
        button.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String phId = sharedPreferences.getString("phId", "");
        String location = sharedPreferences.getString("location", "");
        String apikey = getResources().getString(R.string.apikey);
        String endpointLlamadas = getResources().getString(R.string.endpointLlamadas);

        if (!phId.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String timestamp = sdf.format(new Date());

            CommandRequest request = new CommandRequest(text, location, timestamp);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(endpointLlamadas)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CommandService service = retrofit.create(CommandService.class);
            Call<Void> call = service.executeCommand(apikey, phId, request);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        messageTextView.setText("Operación Realizada!");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(AccessControlActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 3000);
                    } else {
                        Toast.makeText(AccessControlActivity.this, "Este botón no tiene una correcta configuración", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(AccessControlActivity.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(AccessControlActivity.this, "No se ha encontrado un código válido", Toast.LENGTH_SHORT).show();
        }
        deshabilitarBotones(button);
    }
}

