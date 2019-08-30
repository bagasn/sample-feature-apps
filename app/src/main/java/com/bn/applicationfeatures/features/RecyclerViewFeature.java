package com.bn.applicationfeatures.features;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bn.applicationfeatures.R;
import com.bn.applicationfeatures.app.model.RecyclerAdapterModel;
import com.bn.applicationfeatures.app.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewFeature extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerAdapter adapter;
    private List<RecyclerAdapterModel> itemList;

    private boolean isScrooling = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_recyclerview);

        recycler = findViewById(R.id.recyclerView);
        recycler.setHasFixedSize(false);

        itemList = getItemLists();
        adapter = new RecyclerAdapter(this, itemList);
        recycler.setAdapter(adapter);

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScrooling = false;
                } else {
                    isScrooling = true;
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
//                moveMe();
            }
        });
        (findViewById(R.id.fabDelete))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem();
                    }
                });
    }

    String TAG = "KusoYaro";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: shit");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: 1");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: 1");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: 1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: 1");
    }

    void moveMe() {
        Intent inten = new Intent(this, DownloadFeature.class);
        startActivityForResult(inten, 1);
    }

    private List<RecyclerAdapterModel> getItemLists() {
        List<RecyclerAdapterModel> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            RecyclerAdapterModel item = new RecyclerAdapterModel();
            item.setId(0);
            if (i % 2 == 0)
                item.setImageResource(R.drawable.ic_error_outline_black_24dp);
            else
                item.setImageResource(R.drawable.ic_add_black_24dp);

            item.setTitle("The Title " + (i + 1));
            item.setContent("Content " + (i + 1));

            items.add(item);
        }

        return items;
    }

    private void addNewItem() {
        Random r = new Random();

        RecyclerAdapterModel item = new RecyclerAdapterModel();
        item.setId(r.nextInt(100));
        item.setTitle("The Fucking Title " + item.getId());
        item.setContent("The Fucking Content " + item.getId());
        item.setImageResource(R.drawable.ic_add_black_24dp);

        if (isScrooling) {
            Toast.makeText(this, "Can't add item when scrolling",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        itemList.add(3, item);
        adapter.notifyItemInserted(3);
        adapter.notifyItemRangeChanged(0, itemList.size());
//        View view = recycler.getFocusedChild();
//        recycler.child
//        recycler.requestChildFocus(view, );
//        adapter.notifyDataSetChanged();
    }

    private void deleteItem() {
        if (isScrooling) {
            Toast.makeText(this, "Can't delete item when scrolling",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        itemList.remove(5);
        adapter.notifyItemRemoved(5);
        adapter.notifyItemRangeChanged(0, itemList.size());
    }

}
