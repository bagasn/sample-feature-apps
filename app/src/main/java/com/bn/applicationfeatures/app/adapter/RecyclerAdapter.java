package com.bn.applicationfeatures.app.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bn.applicationfeatures.R;
import com.bn.applicationfeatures.app.model.RecyclerAdapterModel;
import com.bn.applicationfeatures.app.adapter.holdeer.RecyclerHolder;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

    private Context mContext;
    private List<RecyclerAdapterModel> itemList;

    public RecyclerAdapter(Context context, List<RecyclerAdapterModel> items) {
        mContext = context;
        itemList = items;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_recycler_feature, viewGroup, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int i) {

        RecyclerAdapterModel item = itemList.get(i);

//        holder.container.setAnimation(
//                AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_in));

        if (item != null) {
            holder.image.setImageResource(item.getImageResource());
            holder.title.setText(item.getTitle());
            holder.content.setText(item.getContent());
        } else {
            holder.image.setImageResource(0);
            holder.title.setText("");
            holder.content.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(int pos, RecyclerAdapterModel item) {
        itemList.add(pos, item);
        notifyItemInserted(pos);
        notifyItemChanged(pos, itemList.size());
    }

    public void removeItem(int pos) {
        itemList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemChanged(pos, itemList.size());
    }
}
