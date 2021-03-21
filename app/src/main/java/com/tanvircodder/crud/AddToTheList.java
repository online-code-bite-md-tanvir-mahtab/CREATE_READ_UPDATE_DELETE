package com.tanvircodder.crud;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.tanvircodder.crud.database.CURDContract;

public class AddToTheList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
//    nwo i am going to have the loader id ...//
    private static final int LOADER_ID = 1;
//    now i need to declare the uri..//
    private Uri mUri;
    private static final String LOG_TAG = AddToTheList.class.getSimpleName();
    private EditText mEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_the_list_activity);
        mEditText = (EditText) findViewById(R.id.edit_text);
        Intent data = getIntent();
        mUri = data.getData();
//        now going ot check if the uri is null or not..//
        if (mUri == null){
            Toast.makeText(getApplicationContext(),"Thier is no data ",Toast.LENGTH_SHORT).show();
            getSupportActionBar().setTitle("Add To List");
        }else {
            Toast.makeText(getApplicationContext(),"Their is some data :" + mUri,Toast.LENGTH_SHORT).show();
            //            if their is no data then the add to list will become the update to the list..//
            getSupportActionBar().setTitle("Update");
        }
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }
    /*now i am going to create a onclick mathod...
    * that will contain the View as its */
    public void onAddList(View view){
//        now i am going to get the text from the editTextView...//
        String name = mEditText.getText().toString();
//        nwo going to check if the string name is null or have data
        if (name.isEmpty()){
            Toast.makeText(getApplicationContext(),"Their is no data!!",Toast.LENGTH_LONG).show();
        }else{
            ContentValues values = new ContentValues();
            values.put(CURDContract.ListEntry.BOOK_NAME,name);
            if (mUri == null){
                Uri insertUri = getContentResolver().insert(CURDContract.ListEntry.CONTENT_URI,values);
                if (insertUri == null){
                    Toast.makeText(getBaseContext(),"faild to insert the data",Toast.LENGTH_LONG).show();
                }else {
                    Log.e(LOG_TAG, "data is inserted into the :" + values);
                    finish();
                }
            }else {
                // TODO: 3/21/2021 thier is some problem
                int rowsDeleted = getContentResolver().update(mUri,values,null,null);
                if (rowsDeleted == 0){
                    Toast.makeText(this,"The data wasen't able to update",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"the data is updated ",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
     /*   String[] projectio = {
                CURDContract.ListEntry.BOOK_NAME
        };
        switch (id){
            case LOADER_ID:
                return new CursorLoader(this,mUri,
                        projectio,
                        null,
                        null,
                        null);
            default:
                throw new  RuntimeException("Loader Not Implemented : " + id);
        }*/
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mData = null;
            @Override
            protected void onStartLoading() {
                if (mData != null){
                    deleverResult(mData);
                }else {
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(mUri,
                            null,
                            null,
                            null,
                            null);
                }catch (SQLException e){
                    return  null;
                }
            }
            public void deleverResult(Cursor data){
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // TODO: 3/21/2021 I am having some problem while geting the data....
        mEditText = (EditText) findViewById(R.id.edit_text);
//        nwo i am going to create an boolean variable and set it to false..//
        boolean theirIsData = false;
        if (data != null && data.moveToFirst()){
            theirIsData = true;
            Toast.makeText(getApplicationContext(),"The data : " + data,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"The data is empty : " ,Toast.LENGTH_SHORT).show();
            return;
        }
        int columnBookTitle = data.getColumnIndex(CURDContract.ListEntry.BOOK_NAME);
        String bookToUpdate = data.getString(columnBookTitle);
        Toast.makeText(getApplicationContext(),"The data : " + bookToUpdate,Toast.LENGTH_SHORT).show();
//        nwo i am going to set the text ..//
        mEditText.setText(bookToUpdate);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mEditText.setText("");
    }
}
