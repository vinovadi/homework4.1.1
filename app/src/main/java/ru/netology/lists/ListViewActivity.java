package ru.netology.lists;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private SharedPreferences myListSharedPref;
    private static String LIST_TEXT = "list_text";
    private List<Map<String, String>> values;
    private BaseAdapter listContentAdapter;
    private SwipeRefreshLayout swipeLayout;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = findViewById(R.id.list);
        saveDateInSharedPref();

        values = prepareContent();

        listContentAdapter = createAdapter(values);

        list.setAdapter(listContentAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                values.remove(position);
                listContentAdapter.notifyDataSetChanged();
            }
        });

        swipeToRefresh();
    }

    @NonNull
    private BaseAdapter createAdapter(List<Map<String, String>> values) {
        return new SimpleAdapter(this, values, R.layout.simple_adapter_view, new String[]{"content", "header"}, new int[]{R.id.topText, R.id.bottomText});
    }

    @NonNull
    private List<Map<String, String>> prepareContent() {
        List<Map<String, String>> prepareValues = new ArrayList<>();

        String[] arrayContent = myListSharedPref.getString(LIST_TEXT, "").split("\n\n");

        for (int i = 0; i < arrayContent.length; i++) {
            int key = arrayContent[i].length();
            Map<String, String> itemsMap = new HashMap<>();
            itemsMap.put("header", String.valueOf(key));
            itemsMap.put("content", arrayContent[i]);
            prepareValues.add(itemsMap);
        }
        return prepareValues;
    }

    private void saveDateInSharedPref() {
        myListSharedPref = getSharedPreferences("MyList", MODE_PRIVATE);
        if (!myListSharedPref.contains(LIST_TEXT)) {
            SharedPreferences.Editor myEditor = myListSharedPref.edit();
            String listTxt = getText(R.string.large_text).toString();
            myEditor.putString(LIST_TEXT, listTxt);
            myEditor.apply();
        }
    }

    private void updateList() {
        values = prepareContent();
        listContentAdapter = createAdapter(values);
        list.setAdapter(listContentAdapter);
    }

    private void swipeToRefresh() {
        swipeLayout = findViewById(R.id.swipeRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateList();
                listContentAdapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        });
    }
}
