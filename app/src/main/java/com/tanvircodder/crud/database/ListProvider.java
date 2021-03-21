package com.tanvircodder.crud.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
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
        final SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch(match){
            case CRUD:
                cursor = database.query(CURDContract.ListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CRUD_WITH_ID:
                String id = uri.getLastPathSegment();
                selection = CURDContract.ListEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
                cursor = database.query(CURDContract.ListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new SQLException("faild to load data");
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
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
//        geting the access from the cursor..//
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case CRUD:
                rowsDeleted = database.delete(CURDContract.ListEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case CRUD_WITH_ID:
                String id = uri.getPathSegments().get(1);
                selection =CURDContract.ListEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(id)};
                rowsDeleted   = database.delete(CURDContract.ListEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new SQLException("Problem deleting the data to the uri :" + uri);
        }
        if (rowsDeleted !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated;
        int match = sUriMatcher.match(uri);
        switch (match){
            case CRUD:
                rowsUpdated = database.update(CURDContract.ListEntry.TABLE_NAME,values,null,null);
                break;
            case CRUD_WITH_ID:
                String id = uri.getLastPathSegment();
                selection = CURDContract.ListEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
                rowsUpdated = database.update(CURDContract.ListEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw  new SQLException("The update is cann't be performed :" + uri);
        }
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
}
