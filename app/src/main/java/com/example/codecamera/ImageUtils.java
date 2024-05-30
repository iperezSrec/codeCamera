package com.example.codecamera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Base64;
import android.widget.ImageView;

public class ImageUtils {

    public static void applyColorMaskAndTintFromSharedPreferences(Context context, ImageView imageView, int color, String imageKey) {

        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String encodedImage = preferences.getString(imageKey, "");
        Bitmap bitmap = decodeBase64(encodedImage);

        Bitmap tintedBitmap = applyColorMask(bitmap, color);

        imageView.setImageBitmap(tintedBitmap);
    }

    private static Bitmap applyColorMask(Bitmap bitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(getColorMatrix(color)));

        canvas.drawBitmap(bitmap, 0, 0, paint);

        return resultBitmap;
    }

    private static ColorMatrix getColorMatrix(int color) {
        float r = Color.red(color) / 255f;
        float g = Color.green(color) / 255f;
        float b = Color.blue(color) / 255f;

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                r, 0, 0, 0, 0,
                0, g, 0, 0, 0,
                0, 0, b, 0, 0,
                0, 0, 0, 1, 0
        });

        return colorMatrix;
    }

    private static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}

