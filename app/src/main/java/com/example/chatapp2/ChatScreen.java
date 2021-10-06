package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class ChatScreen extends AppCompatActivity {

    ListView listView;
    String[] name = {"Thùy Linh", "Đình Tài", "Quang Huy", "Việt Hương"};
    ArrayAdapter<String>arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        listView = findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.activity_list_item, name);
        listView.setAdapter(arrayAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.iconSearch);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Enter phone, messenger...");
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                arrayAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
}