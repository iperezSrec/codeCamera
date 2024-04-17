package com.example.codecamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String CORRECT_CODE = "123456"; // Código de acceso correcto
    private StringBuilder enteredCode = new StringBuilder();

    private ImageView[] dots;

    private static final int CAMERA_PERMISSION_REQUEST = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;

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
                // Iniciar la nueva actividad ForgotPasswordActivity
                Intent intent = new Intent(MainActivity.this, notiContra.class);
                startActivity(intent);
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

                // Verificar y solicitar permiso de la cámara si es necesario
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST);
                } else {
                    // Permiso de cámara concedido, iniciar la captura de foto
                    dispatchTakePictureIntent();
                }

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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "No se encontró ninguna aplicación de cámara", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de cámara concedido, iniciar la captura de foto
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Foto capturada con éxito, obtener la imagen
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Guardar la imagen en el almacenamiento externo
            saveImageToExternalStorage(imageBitmap);
        }
    }

    private void saveImageToExternalStorage(Bitmap imageBitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imageFileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.flush();
            outputStream.close();

            // Escanear la imagen para que aparezca en la galería
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    imageFile.getAbsolutePath(), imageFileName, null);

            Toast.makeText(this, "Imagen guardada en " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
        }
    }
}
