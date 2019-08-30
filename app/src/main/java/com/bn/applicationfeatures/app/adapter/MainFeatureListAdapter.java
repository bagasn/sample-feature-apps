package com.bn.applicationfeatures.app.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bn.applicationfeatures.R;
import com.bn.applicationfeatures.app.model.MainFeatureModel;

import java.util.List;

public class MainFeatureListAdapter extends ArrayAdapter<MainFeatureModel> {

    private static final int LAYOUT_RESOURCE = R.layout.row_main_feature;

    public MainFeatureListAdapter(@NonNull Context context,
                                  @NonNull List<MainFeatureModel> objects) {
        super(context, LAYOUT_RESOURCE, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext())
                    .inflate(LAYOUT_RESOURCE, parent, false);

        ImageView image = view.findViewById(R.id.imageView);
        TextView text = view.findViewById(R.id.textTitle);

        MainFeatureModel item = getItem(position);

        if (item != null) {
            image.setImageResource(item.getResource());
            text.setText(item.getTitle());
        }

        return view;
    }
}
