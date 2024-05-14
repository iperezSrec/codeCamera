package com.example.codecamera;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class IdentityVerificationActivity extends AppCompatActivity {

    private LinearLayout responseContainer;
    private LinearLayout fotoContaine;
    private LinearLayout fotoContainer1;
    private LinearLayout fotoContainer2;

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
        }

        responseContainer = findViewById(R.id.responseContainer);
        fotoContaine = findViewById(R.id.fotoContaine);
        fotoContainer1 = findViewById(R.id.fotoContainer1);
        fotoContainer2 = findViewById(R.id.fotoContainer2);

        // Crear OrientationEventListener para detectar cambios de orientación
        orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                // Verificar si la orientación del dispositivo es vertical
                if (isPortraitOrientation()) { // Cambiar a orientación vertical
                    responseContainer.setOrientation(LinearLayout.VERTICAL);
                    fotoContaine.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParamsFotoContainer1 = (LinearLayout.LayoutParams) fotoContainer1.getLayoutParams();
                    layoutParamsFotoContainer1.height = 0;
                    fotoContainer1.setLayoutParams(layoutParamsFotoContainer1);
                    fotoContainer1.setVisibility(View.INVISIBLE);
                    // Establecer margen de inicio para fotoContainer2
                    LinearLayout.LayoutParams layoutParamsFotoContainer2 = (LinearLayout.LayoutParams) fotoContainer2.getLayoutParams();
                    layoutParamsFotoContainer2.setMarginStart(200);
                    layoutParamsFotoContainer2.setMarginEnd(0);
                    fotoContainer2.setLayoutParams(layoutParamsFotoContainer2);

                    // Establecer margen superior para fotoContaine
                    LinearLayout.LayoutParams layoutParamsFotoContaine = (LinearLayout.LayoutParams) fotoContaine.getLayoutParams();
                    layoutParamsFotoContaine.topMargin = 50;
                    fotoContaine.setLayoutParams(layoutParamsFotoContaine);
                } else { // Mantener la orientación horizontal
                    responseContainer.setOrientation(LinearLayout.HORIZONTAL);
                }
            }
        };

        // Comenzar a escuchar los cambios de orientación
        orientationEventListener.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener la escucha de cambios de orientación al destruir la actividad
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
        Intent intent = new Intent(this, AccessControlActivity.class);
        startActivity(intent);
    }

    public void onNoClicked(View view) {
        finish();
    }
}