package com.example.codecamera;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfigurationDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflar la vista personalizada del diálogo
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_configuration, null);

        // Obtener referencias a los EditTexts
        EditText editText1 = dialogView.findViewById(R.id.btn1);
        EditText editText2 = dialogView.findViewById(R.id.btn2);
        EditText editText3 = dialogView.findViewById(R.id.btn3);

        // Recuperar los valores guardados en SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String savedText1 = sharedPreferences.getString("text1", "");
        String savedText2 = sharedPreferences.getString("text2", "");
        String savedText3 = sharedPreferences.getString("text3", "");
        int savedSelectedNumber = sharedPreferences.getInt("selectedNumber", 1); // Valor predeterminado: mostrar 1 EditText

        // Establecer los valores recuperados en los EditTexts
        editText1.setText(savedText1);
        editText2.setText(savedText2);
        editText3.setText(savedText3);

        // Obtener referencia al Spinner desde la vista inflada
        Spinner spinnerOptions = dialogView.findViewById(R.id.spinnerOptions);

        // Crear un adaptador para el Spinner con opciones del 1 al 3
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new String[]{"1", "2", "3"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el adaptador en el Spinner
        spinnerOptions.setAdapter(adapter);

        // Seleccionar el número guardado en el Spinner
        spinnerOptions.setSelection(savedSelectedNumber - 1); // Restar 1 porque los índices en Spinner comienzan en 0

        // Listener para el Spinner
        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el número seleccionado del Spinner
                int selectedNumber = Integer.parseInt((String) parent.getItemAtPosition(position));

                // Mostrar u ocultar EditTexts según el número seleccionado
                showEditTexts(dialogView, selectedNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No es necesario implementar nada aquí
            }
        });

        // Encontrar el botón personalizado "Aceptar" en la vista del diálogo
        Button buttonAceptar = dialogView.findViewById(R.id.buttonAceptar);

        // Configurar el evento de clic para el botón "Aceptar"
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedNumber = spinnerOptions.getSelectedItemPosition() + 1;

                // Validar los textos ingresados
                boolean isValid = validateInputs(dialogView, selectedNumber);
                if (isValid) {
                    saveChanges(dialogView, selectedNumber);
                    dismiss(); // Cerrar el diálogo
                } else {
                    // Mostrar mensaje de error porque no se ingresaron las tres palabras correctas
                    Toast.makeText(requireContext(), "Por favor, ingresa las tres palabras válidas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Construir el diálogo utilizando AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Configuración");
        builder.setView(dialogView);

        // No es necesario agregar botones estándar (Aceptar/Cancelar) aquí

        // Crear y retornar el AlertDialog
        return builder.create();
    }

    private boolean validateInputs(View dialogView, int selectedNumber) {
        EditText editText1 = dialogView.findViewById(R.id.btn1);
        EditText editText2 = dialogView.findViewById(R.id.btn2);
        EditText editText3 = dialogView.findViewById(R.id.btn3);

        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();
        String text3 = editText3.getText().toString().trim();

        // Validar los textos ingresados
        boolean isValidText1 = isValidButtonText(text1);
        boolean isValidText2 = isValidButtonText(text2);
        boolean isValidText3 = isValidButtonText(text3);

        // Mostrar un mensaje de error si no se ingresaron las tres palabras válidas
        if (selectedNumber == 1 && isValidText1) {
            return true;
        } else if (selectedNumber == 2 && isValidText1 && isValidText2) {
            return true;
        } else if (selectedNumber == 3 && isValidText1 && isValidText2 && isValidText3) {
            return true;
        }

        return false;
    }



    private void showEditTexts(View dialogView, int selectedNumber) {
        EditText editText1 = dialogView.findViewById(R.id.btn1);
        EditText editText2 = dialogView.findViewById(R.id.btn2);
        EditText editText3 = dialogView.findViewById(R.id.btn3);

        // Limpiar el contenido de los EditText que se ocultan
        switch (selectedNumber) {
            case 1:
                // Solo mostrar editText1 y limpiar editText2 y editText3
                editText1.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText2.setText(""); // Limpiar el contenido de editText2
                editText3.setText(""); // Limpiar el contenido de editText3
                break;
            case 2:
                // Mostrar editText1 y editText2, y limpiar editText3
                editText1.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);
                editText3.setVisibility(View.GONE);
                editText3.setText(""); // Limpiar el contenido de editText3
                break;
            case 3:
                // Mostrar todos los editTexts
                editText1.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);
                editText3.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void saveChanges(View dialogView, int selectedNumber) {
        EditText editText1 = dialogView.findViewById(R.id.btn1);
        EditText editText2 = dialogView.findViewById(R.id.btn2);
        EditText editText3 = dialogView.findViewById(R.id.btn3);

        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();
        String text3 = editText3.getText().toString().trim();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("text1", text1);
        editor.putString("text2", text2);
        editor.putString("text3", text3);
        editor.putInt("selectedNumber", selectedNumber);
        editor.apply();

        // Mostrar el mensaje de cambios guardados solo si se realizaron cambios válidos
        Toast.makeText(requireContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
    }

    private boolean isValidButtonText(String text) {
        return text.equals("Fichar") || text.equals("Desfichar") || text.equals("Entrada");
    }
}

