package com.example.codecamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String CORRECT_CODE = "111111111"; // Código de acceso correcto de 9 caracteres
    private StringBuilder enteredCode = new StringBuilder();

    private ImageView[] dots;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this, "trabajadores.db", 1);

        try {
            databaseHelper.CheckDatabase();
        } catch (Exception e) {
        }
        try {
            databaseHelper.OpenDatabase();
        } catch (Exception e) {
        }

        // Obtener referencias a los indicadores circulares
        dots = new ImageView[9]; // Ajustar a 9 indicadores
        dots[0] = findViewById(R.id.dot1);
        dots[1] = findViewById(R.id.dot2);
        dots[2] = findViewById(R.id.dot3);
        dots[3] = findViewById(R.id.dot4);
        dots[4] = findViewById(R.id.dot5);
        dots[5] = findViewById(R.id.dot6);
        dots[6] = findViewById(R.id.dot7);
        dots[7] = findViewById(R.id.dot8);
        dots[8] = findViewById(R.id.dot9);

        // Referenciar el TextView de "¿Olvidaste tu contraseña de acceso?"
        TextView textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        // Configurar listener para el TextView de "¿Olvidaste tu contraseña?"
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la nueva actividad ForgotPasswordActivity
                Intent intent = new Intent(MainActivity.this, notiContra.class);
                startActivity(intent);
            }
        });
    }

    public void onDigitButtonClick(View view) {
        if (enteredCode.length() < 9) {
            Button button = (Button) view;
            enteredCode.append(button.getText().toString());
            updateIndicators();
        }
    }

    public void onClearButtonClick(View view) {
        enteredCode.setLength(0); // Limpiar el código
        updateIndicators(); // Actualizar los indicadores (círculos vacíos)
    }

    private void updateIndicators() {
        for (int i = 0; i < dots.length; i++) {
            if (i < enteredCode.length()) {
                dots[i].setImageResource(R.drawable.circle_filled); // Marcar indicador (círculo lleno)
            } else {
                dots[i].setImageResource(R.drawable.circle_empty); // Restablecer indicador (círculo vacío)
            }
        }

        if (enteredCode.length() == 9) {
            // Verificar el código ingresado
            if (enteredCode.toString().equals(CORRECT_CODE)) {
                // Código correcto, desbloquear la aplicación
                Toast.makeText(this, "¡Desbloqueado!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Confirmacion.class);
                startActivity(intent);

                // Limpiar el código después de intentar desbloquear
                enteredCode.setLength(0);
                updateIndicators();
            } else {
                // Código incorrecto, restablecer indicadores (círculos vacíos)
                enteredCode.setLength(0);
                updateIndicators();
                Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
