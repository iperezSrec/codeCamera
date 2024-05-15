package com.example.codecamera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codecamera.db.DatabaseHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    LinearLayout scan_btn;

    private StringBuilder enteredCode = new StringBuilder();
    private ImageView[] dots;
    private DatabaseHelper databaseHelper;

    private TextView textViewForgotPassword;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan_btn = findViewById(R.id.scanner);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Escanea el código QR");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
                intentIntegrator.initiateScan();
            }
        });


        ImageView logoImageView = findViewById(R.id.logo);
        int logoResourceId = R.drawable.imagenEmpresa;
        logoImageView.setImageResource(logoResourceId);

        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.checkAndRecreateTable();

        databaseHelper = new DatabaseHelper(this);

        dots = new ImageView[9];
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

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordDialog dialogFragment = new ForgotPasswordDialog();
                dialogFragment.show(getSupportFragmentManager(), "ForgotPasswordDialogFragment");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("phId", contents);
                editor.apply();
                checkAndProceed(contents);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkAndProceed(String phId) {
        if (isValidCode(phId)) {
            showConfirmationScreen(phId);
        } else {
            Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDigitButtonClick(View view) {
        if (enteredCode.length() < 9) {
            Button button = (Button) view;
            enteredCode.append(button.getText().toString());
            updateIndicators();
        }
    }

    public void onClearButtonClick(View view) {
        enteredCode.setLength(0);
        updateIndicators();
    }

    private void updateIndicators() {
        for (int i = 0; i < dots.length; i++) {
            if (i < enteredCode.length()) {
                dots[i].setImageResource(R.drawable.circle_filled);
            } else {
                dots[i].setImageResource(R.drawable.circle_empty);
            }
        }

        if (enteredCode.length() == 9) {
            String phId = enteredCode.toString();
            checkAndProceed(phId);
            enteredCode.setLength(0);
            updateIndicators();
        }
    }

    private boolean isValidCode(String phId) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String[] columns = {"phId"};
        String selection = "phId = ?";
        String[] selectionArgs = {phId};
        Cursor cursor = db.query("usuarios", columns, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    private void showConfirmationScreen(String phId) {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phId", phId);
        editor.apply();

        String name = getNameFromDatabase(phId);
        String role = getRoleFromDatabase(phId);

        Intent intent = new Intent(this, IdentityVerificationActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("ROLE", role);
        startActivity(intent);
    }

    @SuppressLint("Range")
    private String getNameFromDatabase(String phId) {
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

    @SuppressLint("Range")
    private String getRoleFromDatabase(String phId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {"role"};
        String selection = "phId = ?";
        String[] selectionArgs = {phId};
        Cursor cursor = db.query("usuarios", columns, selection, selectionArgs, null, null, null);
        String role = "";
        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndex("role"));
        }
        cursor.close();
        return role;
    }
}
