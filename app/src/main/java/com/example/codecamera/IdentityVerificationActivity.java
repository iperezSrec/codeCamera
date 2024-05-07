package com.example.codecamera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.codecamera.api.ImageService;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdentityVerificationActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_verification);

        String name = getIntent().getStringExtra("NAME");
        String role = getIntent().getStringExtra("ROLE");

        TextView textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewQuestion.setText("¿Eres " + name + "?");

        ImageButton imageButton = findViewById(R.id.buttonConfiguration);
        if (role.equals("admin")) {
            imageButton.setVisibility(View.VISIBLE);
        }

        ImageButton buttonConfiguration = findViewById(R.id.buttonConfiguration);
        buttonConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfigurationDialog();
            }
        });
    }

    private void showConfigurationDialog() {
        ConfigurationDialog dialogFragment = new ConfigurationDialog();
        dialogFragment.show(getSupportFragmentManager(), "ConfigurationDialogFragment");
    }

    public void onYesClicked(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            dispatchTakePictureIntent();
        }
    }

    public void onNoClicked(View view) {
        finish();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
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
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            saveImageAsBase64(imageBitmap);

            Intent intent = new Intent(this, AccessControlActivity.class);
            startActivity(intent);
        }
    }

    private void saveImageAsBase64(Bitmap imageBitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        imageBitmap.recycle();

        RequestBody image = RequestBody.create(MediaType.parse("application/octet-stream"), byteArray);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://file-store-proxy.pro.srec.solutions/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImageService service = retrofit.create(ImageService.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String timestamp = sdf.format(new Date());

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String phId = sharedPreferences.getString("phId", "");

        Call<Void> call = service.uploadImage(timestamp+"_"+phId, image);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(IdentityVerificationActivity.this, "Foto enviada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(IdentityVerificationActivity.this, "Foto no enviada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(IdentityVerificationActivity.this, "Error en la comunicación con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}