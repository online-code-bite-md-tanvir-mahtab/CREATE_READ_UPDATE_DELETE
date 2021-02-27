package com.tanvircodder.crud.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.tanvircodder.crud.AddToTheList;

public class CURDContract {
    public static final String AUTHORITY = "com.tanvircodder.crud.database";
//    now i am going to have the uri..//
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ AUTHORITY);
    public static final String PATH = "crud";
    /*in heir i will add the authority and path and other things*/
    public static class ListEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH)
                .build();
//        for the table name..//
        public static final String TABLE_NAME = "crud";
        public static final String BOOK_NAME = "books";
    }
}
