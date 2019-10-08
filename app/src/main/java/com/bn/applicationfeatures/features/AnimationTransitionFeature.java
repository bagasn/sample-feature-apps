package com.bn.applicationfeatures.features;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bn.applicationfeatures.R;
import com.bn.applicationfeatures.app.core.AppActivity;

public class AnimationTransitionFeature extends AppActivity {

    private ListView listView;

    private String[] labels = new String[]{
            "Item Test 1",
            "Item Test 2",
            "Item Test 3",
            "Item Test 4",
            "Item Test 5"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_animation_transition);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listContent);
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, labels);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemSelected(position);
            }
        });
    }

    private void onItemSelected(int pos) {
        switch (pos) {
            case 0:

                break;
            default:
                Toast.makeText(this, "Not available now", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }

}
