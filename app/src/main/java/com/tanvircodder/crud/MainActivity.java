package com.tanvircodder.crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
//    nwo i am going to declare the recyclerView../
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        nwo i am going to findout the layout...///
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        adding the layoutManger to the recyclerView...//
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        now I need to set teh adapter to the view..//
    }
}