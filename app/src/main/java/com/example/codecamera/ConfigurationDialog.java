package com.example.codecamera;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfigurationDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_configuration, null);

        EditText editText1 = dialogView.findViewById(R.id.btn1);
        EditText editText2 = dialogView.findViewById(R.id.btn2);
        EditText editText3 = dialogView.findViewById(R.id.btn3);
        EditText editText4 = dialogView.findViewById(R.id.location);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String savedText1 = sharedPreferences.getString("text1", "");
        String savedText2 = sharedPreferences.getString("text2", "");
        String savedText3 = sharedPreferences.getString("text3", "");
        String savedText4 = sharedPreferences.getString("location", "");

        editText1.setText(savedText1);
        editText2.setText(savedText2);
        editText3.setText(savedText3);
        editText4.setText(savedText4);

        Button buttonAceptar = dialogView.findViewById(R.id.buttonAceptar);

        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = validateInputs(dialogView);
                if (isValid) {
                    saveChanges(dialogView);
                    dismiss();
                } else {
                    Toast.makeText(requireContext(), "Por favor, ingresa un valor para la ubicación", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Configuración");
        builder.setView(dialogView);

        return builder.create();
    }

    private boolean validateInputs(View dialogView) {
        EditText editText1 = dialogView.findViewById(R.id.btn1);
        EditText editText2 = dialogView.findViewById(R.id.btn2);
        EditText editText3 = dialogView.findViewById(R.id.btn3);
        EditText editText4 = dialogView.findViewById(R.id.location);

        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();
        String text3 = editText3.getText().toString().trim();
        String location = editText4.getText().toString().trim();

        // Verificar si el campo de la ubicación está vacío
        return !location.isEmpty();
    }

    private void saveChanges(View dialogView) {
        EditText editText1 = dialogView.findViewById(R.id.btn1);
        EditText editText2 = dialogView.findViewById(R.id.btn2);
        EditText editText3 = dialogView.findViewById(R.id.btn3);
        EditText editText4 = dialogView.findViewById(R.id.location);

        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();
        String text3 = editText3.getText().toString().trim();
        String location = editText4.getText().toString().trim();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("text1", text1);
        editor.putString("text2", text2);
        editor.putString("text3", text3);
        editor.putString("location", location);
        editor.apply();

        Toast.makeText(requireContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
    }
}




