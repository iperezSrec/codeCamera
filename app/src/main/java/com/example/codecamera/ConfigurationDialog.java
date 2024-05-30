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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationDialog extends DialogFragment {

    private ImageSpinnerAdapter adapter;
    private Spinner spinner1, spinner2, spinner3;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_configuration, null);

        EditText editText1 = dialogView.findViewById(R.id.btn1);
        EditText editText2 = dialogView.findViewById(R.id.btn2);
        EditText editText3 = dialogView.findViewById(R.id.btn3);
        EditText editText4 = dialogView.findViewById(R.id.location);

        spinner1 = dialogView.findViewById(R.id.spinner1);
        spinner2 = dialogView.findViewById(R.id.spinner2);
        spinner3 = dialogView.findViewById(R.id.spinner3);

        List<SpinnerItem> spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItem(R.drawable.image1, "Imagen 1"));
        spinnerItems.add(new SpinnerItem(R.drawable.image2, "Imagen 2"));
        spinnerItems.add(new SpinnerItem(R.drawable.image3, "Imagen 3"));
        spinnerItems.add(new SpinnerItem(R.drawable.image4, "Imagen 4"));
        spinnerItems.add(new SpinnerItem(R.drawable.image5, "Imagen 5"));
        spinnerItems.add(new SpinnerItem(R.drawable.image6, "Imagen 6"));
        spinnerItems.add(new SpinnerItem(R.drawable.image7, "Imagen 7"));
        spinnerItems.add(new SpinnerItem(R.drawable.image8, "Imagen 8"));
        spinnerItems.add(new SpinnerItem(R.drawable.image9, "Imagen 9"));
        spinnerItems.add(new SpinnerItem(R.drawable.image10, "Imagen 10"));
        spinnerItems.add(new SpinnerItem(R.drawable.image11, "Imagen 11"));
        spinnerItems.add(new SpinnerItem(R.drawable.image12, "Imagen 12"));
        spinnerItems.add(new SpinnerItem(R.drawable.image13, "Imagen 13"));
        spinnerItems.add(new SpinnerItem(R.drawable.image14, "Imagen 14"));
        spinnerItems.add(new SpinnerItem(R.drawable.image15, "Imagen 15"));
        spinnerItems.add(new SpinnerItem(R.drawable.image16, "Imagen 16"));
        spinnerItems.add(new SpinnerItem(R.drawable.image17, "Imagen 17"));
        spinnerItems.add(new SpinnerItem(R.drawable.image18, "Imagen 18"));
        spinnerItems.add(new SpinnerItem(R.drawable.image19, "Imagen 19"));
        spinnerItems.add(new SpinnerItem(R.drawable.image20, "Imagen 20"));
        spinnerItems.add(new SpinnerItem(R.drawable.image21, "Imagen 21"));
        spinnerItems.add(new SpinnerItem(R.drawable.image22, "Imagen 22"));
        spinnerItems.add(new SpinnerItem(R.drawable.image23, "Imagen 23"));
        spinnerItems.add(new SpinnerItem(R.drawable.image24, "Imagen 24"));
        spinnerItems.add(new SpinnerItem(R.drawable.image25, "Imagen 25"));
        spinnerItems.add(new SpinnerItem(R.drawable.image26, "Imagen 26"));
        spinnerItems.add(new SpinnerItem(R.drawable.image27, "Imagen 27"));
        spinnerItems.add(new SpinnerItem(R.drawable.image28, "Imagen 28"));
        spinnerItems.add(new SpinnerItem(R.drawable.image29, "Imagen 29"));
        spinnerItems.add(new SpinnerItem(R.drawable.image30, "Imagen 30"));

        adapter = new ImageSpinnerAdapter(requireContext(), spinnerItems);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);

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

        SpinnerItem selectedItem1 = (SpinnerItem) spinner1.getSelectedItem();
        SpinnerItem selectedItem2 = (SpinnerItem) spinner2.getSelectedItem();
        SpinnerItem selectedItem3 = (SpinnerItem) spinner3.getSelectedItem();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("text1", text1);
        editor.putString("text2", text2);
        editor.putString("text3", text3);
        editor.putString("location", location);
        editor.putInt("image1", selectedItem1.getImageResId());
        editor.putInt("image2", selectedItem2.getImageResId());
        editor.putInt("image3", selectedItem3.getImageResId());

        editor.apply();

        Toast.makeText(requireContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int savedImage1 = sharedPreferences.getInt("image1", -1);
        int savedImage2 = sharedPreferences.getInt("image2", -1);
        int savedImage3 = sharedPreferences.getInt("image3", -1);

        if (savedImage1 != -1) {
            int position1 = adapter.getPositionByImageResId(savedImage1);
            if (position1 != -1) {
                spinner1.setSelection(position1);
            }
        }

        if (savedImage2 != -1) {
            int position2 = adapter.getPositionByImageResId(savedImage2);
            if (position2 != -1) {
                spinner2.setSelection(position2);
            }
        }

        if (savedImage3 != -1) {
            int position3 = adapter.getPositionByImageResId(savedImage3);
            if (position3 != -1) {
                spinner3.setSelection(position3);
            }
        }
    }
}





