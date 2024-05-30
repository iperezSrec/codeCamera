package com.example.codecamera;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ForgotPasswordDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot_password, null);
        AlertDialog alertDialog = builder.setView(view)
                .create();

        Button buttonAceptar = view.findViewById(R.id.buttonAceptar);
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();

        layoutParams.dimAmount = 0.5f;

        alertDialog.getWindow().setAttributes(layoutParams);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        alertDialog.show();

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;

                int desiredWidth = (int) (screenWidth * 0.32 );

                alertDialog.getWindow().setLayout(desiredWidth, WindowManager.LayoutParams.WRAP_CONTENT);

                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        return alertDialog;
    }
}



