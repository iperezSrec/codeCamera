package com.example.codecamera;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class IdentityVerificationActivity extends AppCompatActivity {
    private TextView buttonNo;
    private TextView fotoExplanation2;


    private OrientationEventListener orientationEventListener;

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
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showConfigurationDialog();
                }
            });
        } else {
            imageButton.setVisibility(View.GONE);
        }

        buttonNo = findViewById(R.id.buttonNo);
        fotoExplanation2 = findViewById(R.id.fotoExplanation2);

        orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (isPortraitOrientation()) {
                    buttonNo.setTextSize(45);
                    fotoExplanation2.setTextSize(45);
                } else {

                }
            }
        };

        orientationEventListener.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orientationEventListener.disable();
    }

    private boolean isPortraitOrientation() {
        int orientation = getScreenOrientation();
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private int getScreenOrientation() {
        Display display = getWindowManager().getDefaultDisplay();
        int rotation = display.getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                return Configuration.ORIENTATION_PORTRAIT;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                return Configuration.ORIENTATION_LANDSCAPE;
            default:
                return Configuration.ORIENTATION_UNDEFINED;
        }
    }

    private void showConfigurationDialog() {
        ConfigurationDialog dialogFragment = new ConfigurationDialog();
        dialogFragment.show(getSupportFragmentManager(), "ConfigurationDialogFragment");
    }

    public void onYesClicked(View view) {
        Intent intent = new Intent(IdentityVerificationActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    public void onNoClicked(View view) {
        finish();
    }
}
