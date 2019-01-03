package com.example.anasamin.borrowfromme.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class dataprovider extends ContentProvider {
    public static final int OBJ=100;
    public static final int OBJ_ID=101;
    private static final UriMatcher urimatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static {
        urimatcher.addURI(object.CONTENT_AUTHORITY,object.PATH,OBJ);
        urimatcher.addURI(object.CONTENT_AUTHORITY,object.PATH+"/#",OBJ_ID);
    }
    public static final String LOG_TAG=dataprovider.class.getSimpleName();
    private helper help;
    @Override
    public boolean onCreate() {
        help=new helper(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase db=help.getReadableDatabase();
        Cursor cursor;
        int URIMATCHER=urimatcher.match(uri);
        switch(URIMATCHER){
            case 100:{
                cursor=db.query(object.column.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

                break;
            }
            case 101:{
                selection=object.column.Column_ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor=db.query(object.column.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
            }
            default:{
                throw new IllegalArgumentException("NOT ACCEPTABLE QUERY");
            }
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        int URIMATCH=urimatcher.match(uri);
        switch(URIMATCH){
            case OBJ:{
                return object.column.CONTENT_LIST_TYPE;
            }
            case OBJ_ID:{
                return object.column.CONTENT_ITEM_TYPE;
            }
            default:{
                throw new IllegalStateException("state is undefined");
            }
        }
    }

    @Override
    public Uri insert(Uri uri,  ContentValues values) {
       int match=urimatcher.match(uri);
       switch (match){
           case OBJ:{
               return insertobj(uri,values);
           }
           default:{
               throw new IllegalArgumentException("argument is undefined");
           }
       }
    }
    private Uri insertobj(Uri uri,ContentValues values){
        SQLiteDatabase db=help.getWritableDatabase();
        long id=db.insert(object.column.TABLE_NAME,null,values);
        if(id==-1){
            Log.e(LOG_TAG, "id is malformed");
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        int match=urimatcher.match(uri);
        switch(match){
            case OBJ:{
                SQLiteDatabase db=help.getWritableDatabase();
                getContext().getContentResolver().notifyChange(uri,null);
               return db.delete(object.column.TABLE_NAME,selection,selectionArgs);
            }
            default:{
                throw new IllegalArgumentException("matformd uri in delete");
            }
        }
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match=urimatcher.match(uri);
        SQLiteDatabase db=help.getWritableDatabase();
        switch(match){
            case OBJ:{
                int id=db.update(object.column.TABLE_NAME,values,selection,selectionArgs);
                if(id==-1){
                    Log.d(LOG_TAG, "update: Not Possible");
                    return 0;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return id;
            }
            case OBJ_ID:{
                selection =object.column.Column_ID+"=?";
                selectionArgs= new String[]{String.valueOf(ContentUris.parseId(uri))};
                int id=db.update(object.column.TABLE_NAME,values,selection,selectionArgs);
                if(id==-1){
                    Log.d(LOG_TAG, "update: Not Possible");
                    return 0;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return id;
            }
            default:{
                throw new IllegalArgumentException("update not possible");
            }
        }
    }
}
