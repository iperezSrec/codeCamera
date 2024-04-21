package com.example.codecamera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private StringBuilder enteredCode = new StringBuilder();
    private ImageView[] dots;
    private DatabaseHelper databaseHelper;

    private TextView textViewForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this, "trabajadores.db", 1);

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
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        // Agregar OnClickListener al textViewForgotPassword
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad activity_noti_contra
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
            // Verificar el código ingresado consultando la base de datos
            String phId = enteredCode.toString();
            if (isValidCode(phId)) {
                // Código correcto, desbloquear la aplicación
                showConfirmationScreen(phId);
            } else {
                // Código incorrecto, restablecer indicadores (círculos vacíos)
                Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show();
            }
            // Limpiar el código después de intentar desbloquear
            enteredCode.setLength(0);
            updateIndicators();
        }
    }

    private boolean isValidCode(String phId) {
        // Consultar la base de datos para verificar si phId está en trabajadores.phId
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {"phId"};
        String selection = "phId = ?";
        String[] selectionArgs = {phId};
        Cursor cursor = db.query("usuarios", columns, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    private void showConfirmationScreen(String phId) {
        // Obtener el nombre correspondiente a phId desde la base de datos
        String name = getNameFromDatabase(phId);

        // Iniciar la actividad Confirmacion y pasar el nombre como extra
        Intent intent = new Intent(this, Confirmacion.class);
        intent.putExtra("NAME", name);
        startActivity(intent);
    }

    @SuppressLint("Range")
    private String getNameFromDatabase(String phId) {
        // Consultar la base de datos para obtener el nombre asociado a phId
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {"name"};
        String selection = "phId = ?";
        String[] selectionArgs = {phId};
        Cursor cursor = db.query("usuarios", columns, selection, selectionArgs, null, null, null);
        String name = "";
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
        return name;
    }
}


