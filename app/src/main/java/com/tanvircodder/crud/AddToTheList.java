package com.tanvircodder.crud;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tanvircodder.crud.database.CURDContract;

public class AddToTheList extends AppCompatActivity {
    private static final String LOG_TAG = AddToTheList.class.getSimpleName();
    private EditText mEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_the_list_activity);
        mEditText = (EditText) findViewById(R.id.edit_text);
    }
    /*now i am going to create a onclick mathod...
    * that will contain the View as its */
    public void onAddList(View view){
        Toast.makeText(AddToTheList.this,"The button is clicked",Toast.LENGTH_LONG).show();
//        now i am going to get the text from the editTextView...//
        String name = mEditText.getText().toString();
//        nwo going to check if the string name is null or have data
        if (name.isEmpty()){
            Toast.makeText(getApplicationContext(),"Their is no data!!",Toast.LENGTH_LONG).show();
        }else {
            ContentValues values = new ContentValues();
            values.put(CURDContract.ListEntry.BOOK_NAME,name);
            Uri insertUri = getContentResolver().insert(CURDContract.ListEntry.CONTENT_URI,values);
            if (insertUri == null){
                Toast.makeText(getBaseContext(),"faild to insert the data",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getBaseContext(),"data is inserted : " + insertUri.toString(),Toast.LENGTH_LONG).show();
                Log.e(LOG_TAG, "data is inserted into the :" + values);
            }
        }
    }
}
