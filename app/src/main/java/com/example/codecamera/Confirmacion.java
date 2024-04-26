package com.example.codecamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Confirmacion extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);

        // Obtener el nombre pasado como extra desde MainActivity
        String name = getIntent().getStringExtra("NAME");

        // Actualizar el texto del TextView con el nombre correspondiente
        TextView textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewQuestion.setText("¿Eres " + name + "?");

        // Configurar el OnClickListener para el botón de configuración
        ImageButton buttonConfiguration = findViewById(R.id.buttonConfiguration);
        buttonConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar la ventana modal de configuración
                showConfigurationDialog();
            }
        });
    }

    private void showConfigurationDialog() {
        ConfigurationDialogFragment dialogFragment = new ConfigurationDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "ConfigurationDialogFragment");
    }

    public void onYesClicked(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            // Permiso de cámara concedido, iniciar la captura de foto
            dispatchTakePictureIntent();
        }
    }

    public void onNoClicked(View view) {
        finish();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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

            // Guardar la imagen en formato base64 en la ubicación interna
            saveImageAsBase64(imageBitmap);

            // Abrir la actividad 'activity_resultado'
            Intent resultadoIntent = new Intent(this, Resultado.class);
            startActivity(resultadoIntent);
        }
    }

    private void saveImageToExternalStorage(Bitmap imageBitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imageFileName);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

            FileOutputStream fileOutput = new FileOutputStream(imageFile);
            fileOutput.write(outputStream.toByteArray());
            fileOutput.close();

            // Escanear la imagen para que aparezca en la galería
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    imageFile.getAbsolutePath(), imageFileName, null);

            Toast.makeText(this, "Imagen guardada en " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImageAsBase64(Bitmap imageBitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        // Guardar la imagen en formato base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // Guardar la imagen base64 en el directorio especificado
        String dbPath = "/data/data/" + getPackageName() + "/pictures/";
        File dbDir = new File(dbPath);
        if (!dbDir.exists()) {
            dbDir.mkdirs(); // Crea el directorio si no existe
        }
        File imageFile = new File(dbDir, imageFileName);

        try {
            FileWriter writer = new FileWriter(imageFile);
            writer.write(base64Image);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Imagen guardada en " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la imagen en formato base64", Toast.LENGTH_SHORT).show();
        }
    }
}

