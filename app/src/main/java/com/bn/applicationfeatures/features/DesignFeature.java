package com.bn.applicationfeatures.features;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bn.applicationfeatures.R;
import com.bn.applicationfeatures.app.core.AppActivity;
import com.bn.applicationfeatures.features.content.design.DesignDrawerActivity;
import com.bn.applicationfeatures.features.content.design.ui.login.LoginActivity;

public class DesignFeature extends AppActivity {

    private String[] items = new String[]{
            "Login Form",
            "Navigation Drawer"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.listContent);
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemSelected(position);
            }
        });
    }

    private void onItemSelected(int pos) {
        Intent intent = null;
        switch (pos) {
            case 0:
                intent = new Intent(this, LoginActivity.class);
                break;
            case 1:
                intent = new Intent(this, DesignDrawerActivity.class);
                break;
            default:
                Toast.makeText(this,
                        "Not available now", Toast.LENGTH_SHORT)
                        .show();
                break;
        }

        if (intent != null)
            startActivity(intent);
    }
}
