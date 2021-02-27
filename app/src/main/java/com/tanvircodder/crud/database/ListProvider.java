package com.tanvircodder.crud.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListProvider extends ContentProvider {
//    now I need to create the code for the single anddouble...///\
    private static final int CRUD = 100;
    private static final int CRUD_WITH_ID = 101;
//    now I am going to store the mathod to the UriMatcher variable..//
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    //    now i will crete the mathod that will create the urimatcher..//
    public static UriMatcher buildUriMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CURDContract.AUTHORITY, CURDContract.PATH,CRUD);
        matcher.addURI(CURDContract.AUTHORITY,CURDContract.PATH + "/#",CRUD_WITH_ID);
        return matcher;
    }
//    initializing the dbHelper class..//
    private ListDbHelper mDbHelper;
    @Override
    public boolean onCreate() {
        mDbHelper = new ListDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        /*fir i will take the access of the data base to write it */
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
//        now to track of which table we inserted we are going to declare the long variable..//
        int match = sUriMatcher.match(uri);
        Uri newUri;
        switch (match) {
            case CRUD:
                long id = database.insert(CURDContract.ListEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    newUri = ContentUris.withAppendedId(CURDContract.ListEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Faild to insert rows");
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
