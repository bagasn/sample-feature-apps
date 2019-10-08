package com.bn.applicationfeatures;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bn.applicationfeatures.app.model.MainFeatureModel;
import com.bn.applicationfeatures.app.adapter.MainFeatureListAdapter;
import com.bn.applicationfeatures.app.util.FeatureUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listContent);

        List<MainFeatureModel> listContent = new ArrayList<>();
        MainFeatureListAdapter adapter = new MainFeatureListAdapter(this, listContent);

        List<String> itemlist = new ArrayList<>();
        try {
            JSONArray array = new JSONArray("[{\"nama\" : \"bagas\"}]");
            JSONObject obj = array.getJSONObject(0);
            String nama = obj.getString("nama");

            itemlist.add("Item 1");
            itemlist.add("Item 2");
            itemlist.add("Item 3");
            itemlist.add("Item 4");

        } catch (JSONException e) {

        }


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        listContent.addAll(FeatureUtil.getListFeature());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainFeatureModel item = (MainFeatureModel) parent.getItemAtPosition(position);

        if (item != null) {
            Intent intent = new Intent(this, FeatureUtil.getClassById(item.getId()));
            startActivity(intent);
        }
    }
}
