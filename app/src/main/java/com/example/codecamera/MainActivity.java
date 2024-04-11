package com.example.codecamera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String CORRECT_CODE = "123456"; // Código de acceso correcto
    private StringBuilder enteredCode = new StringBuilder();

    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencias a los indicadores circulares
        dots = new ImageView[6];
        dots[0] = findViewById(R.id.dot1);
        dots[1] = findViewById(R.id.dot2);
        dots[2] = findViewById(R.id.dot3);
        dots[3] = findViewById(R.id.dot4);
        dots[4] = findViewById(R.id.dot5);
        dots[5] = findViewById(R.id.dot6);

        // Referenciar el TextView de "¿Olvidaste tu contraseña de acceso?"
        TextView textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        // Configurar listener para el TextView de "¿Olvidaste tu contraseña?"
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se hace clic en el texto de "¿Olvidaste tu contraseña?"
                Toast.makeText(MainActivity.this, "Funcionalidad aún no implementada", Toast.LENGTH_SHORT).show();
                // Aquí puedes agregar código para manejar la acción deseada, como abrir una nueva actividad.
            }
        });
    }

    public void onDigitButtonClick(View view) {
        if (enteredCode.length() < 6) {
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

        if (enteredCode.length() == 6) {
            // Verificar el código ingresado
            if (enteredCode.toString().equals(CORRECT_CODE)) {
                // Código correcto, desbloquear la aplicación
                Toast.makeText(this, "¡Desbloqueado!", Toast.LENGTH_SHORT).show();
                // Aquí puedes iniciar la siguiente actividad o realizar otras acciones
            } else {
                // Código incorrecto, restablecer indicadores (círculos vacíos)
                enteredCode.setLength(0);
                updateIndicators();
                Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
