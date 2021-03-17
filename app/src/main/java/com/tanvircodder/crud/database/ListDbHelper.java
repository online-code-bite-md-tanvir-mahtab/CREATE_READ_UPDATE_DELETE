package com.tanvircodder.crud.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListDbHelper extends SQLiteOpenHelper {
//    now i am going to create database name..//
    private static final String DATABASE_NAME = "person.db";
//    creating the databased version ...///
    private static final int DATA_VERSION = 1;
    public ListDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATA_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        now I am going to create the table..//
        final String SQL_DATA_TABLE = "CREATE TABLE " + CURDContract.ListEntry.TABLE_NAME + " ( "
                + CURDContract.ListEntry._ID + "    INTEGER PRIMARY KEY AUTOINCREMENT , "
                + CURDContract.ListEntry.BOOK_NAME + "  TEXT NOT NULL );";
        db.execSQL(SQL_DATA_TABLE);
    }
/*final String SQL_DATA_TABLE = "CREATE TABLE " + CURDContract.ListEntry.TABLE_NAME + " ( "
                + CURDContract.ListEntry._ID + "    INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CURDContract.ListEntry.BOOK_NAME + "  TEXT NOT NULL );";
        db.execSQL(SQL_DATA_TABLE);*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
