package com.example.codecamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class IdentityVerificationActivity extends AppCompatActivity {

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
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void onNoClicked(View view) {
        finish();
    }
}
