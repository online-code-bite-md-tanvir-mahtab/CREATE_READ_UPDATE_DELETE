package com.tanvircodder.crud;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddToTheList extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_the_list_activity);
    }
    /*now i am going to create a onclick mathod...
    * that will contain the View as its */
    public void onAddList(View view){
        Toast.makeText(AddToTheList.this,"The button is clicked",Toast.LENGTH_LONG).show();
    }
}
