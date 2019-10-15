package ru.netology.lists;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list = findViewById(R.id.list);

        List<Map<String, String>> values = prepareContent();

        BaseAdapter listContentAdapter = createAdapter(values);

        list.setAdapter(listContentAdapter);
    }

    @NonNull
    private BaseAdapter createAdapter(List<Map<String, String>> values) {
        //return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        return new SimpleAdapter(this, values, R.layout.simple_adapter_view, new String[]{"content", "header"}, new int[]{R.id.topText, R.id.bottomText});
    }

    @NonNull
    private List<Map<String, String>> prepareContent() {
        List<Map<String, String>> prepareValues = new ArrayList<>();

        String[] arrayContent = getString(R.string.large_text).split("\n\n");

        for (int i = 0; i < arrayContent.length; i++) {
            int key = arrayContent[i].length();
            Map<String, String> itemsMap = new HashMap<>();
            itemsMap.put("header", String.valueOf(key));
            itemsMap.put("content", arrayContent[i]);
            prepareValues.add(itemsMap);
        }
        return prepareValues;
    }
}
