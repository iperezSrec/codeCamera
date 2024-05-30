package com.example.codecamera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class ImageSpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    private Context mContext;
    private List<SpinnerItem> mItems;

    public ImageSpinnerAdapter(Context context, List<SpinnerItem> items) {
        super(context, 0, items);
        mContext = context;
        mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_elements, parent, false);
        }

        SpinnerItem currentItem = mItems.get(position);

        ImageView imageView = convertView.findViewById(R.id.spinnerImageView);

        imageView.setImageResource(currentItem.getImageResId());

        return convertView;
    }

    public int getPositionByImageResId(int imageResId) {
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).getImageResId() == imageResId) {
                return i;
            }
        }
        return -1;
    }
}
