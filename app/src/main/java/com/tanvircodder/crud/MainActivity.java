package com.tanvircodder.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
//    nwo i am going to declare the recyclerView../
    RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        nwo i am going to findout the layout...///
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        adding the layoutManger to the recyclerView...//
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        now I need to set teh adapter to the view..//
//        creating the instance of the adapter class..//
        mAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_table,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add){
            Intent intent = new Intent(MainActivity.this,AddToTheList.class);
//            now i am going to start the activity..//
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}