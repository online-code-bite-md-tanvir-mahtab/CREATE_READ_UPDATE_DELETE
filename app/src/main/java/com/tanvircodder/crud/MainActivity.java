package com.tanvircodder.crud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tanvircodder.crud.database.CURDContract;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,ListAdapter.clikeMyListener {
    private static final int LOADER_ID = 0;
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
        mAdapter = new ListAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                nwo ia m going to create the tag
                int id = (int) viewHolder.itemView.getTag();
                String stringIds = Integer.toString(id);
                Uri uri = CURDContract.ListEntry.CONTENT_URI;
                // Here is where you'll implement swipe to delete
                uri = uri.buildUpon().appendEncodedPath(stringIds).build();
                getContentResolver().delete(uri,null,null);
                getSupportLoaderManager().restartLoader(LOADER_ID,null,MainActivity.this);
            }
        }).attachToRecyclerView(mRecyclerView);
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                int id = (int) viewHolder.itemView.getTag();
//                String stringId = Integer.toString(id);
//                Intent intent = new Intent(MainActivity.this,AddToTheList.class);
//                Uri uri = CURDContract.ListEntry.CONTENT_URI;
//                uri = uri.buildUpon().appendEncodedPath(stringId).build();
//                intent.setData(uri);
//                startActivity(intent);
//            }
//        }).attachToRecyclerView(mRecyclerView);

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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mTaskData = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mTaskData != null){
                    daliverResult(mTaskData);
                }else {
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                String[] projection = {
                        CURDContract.ListEntry.BOOK_NAME
                };
                try {
                    return getContentResolver().query(CURDContract.ListEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                }catch (SQLException e){
                    Toast.makeText(getApplicationContext(),"Their is an error...",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return null;
                }
            }
            public void daliverResult(Cursor mData){
                mTaskData = mData;
                super.deliverResult(mData);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onClick(int position) {
        // TODO: 3/21/2021 I am pss 
        Intent intent = new Intent(this,AddToTheList.class);
        String stringId = Integer.toString(position);
        Uri intentUri = ContentUris.withAppendedId(CURDContract.ListEntry.CONTENT_URI,position);
        intent.setData(intentUri);
        Toast.makeText(this,"The uri: " + intentUri,Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}