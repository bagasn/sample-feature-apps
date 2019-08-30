package com.bn.applicationfeatures.app.adapter.holdeer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bn.applicationfeatures.R;

public class RecyclerHolder extends RecyclerView.ViewHolder {

    public RelativeLayout container;
    public ImageView image;
    public TextView title;
    public TextView content;

    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);

        container = itemView.findViewById(R.id.container);
        image = itemView.findViewById(R.id.imageView);
        title = itemView.findViewById(R.id.textTitle);
        content = itemView.findViewById(R.id.textContent);
    }
}
