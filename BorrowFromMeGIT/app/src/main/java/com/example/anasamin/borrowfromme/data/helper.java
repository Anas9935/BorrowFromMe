package com.example.anasamin.borrowfromme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class helper extends SQLiteOpenHelper {
    public static final int VERSION=1;
    public static final String FILE_NAME="BorrowMe.db";
    public helper(Context context){
        super(context,FILE_NAME,null,VERSION);
    }
    private static final String SQL_CREATE="CREATE TABLE "+object.column.TABLE_NAME+ " ( " +
            object.column.Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            object.column.FROM + " TEXT, "+
            object.column.TO + " TEXT, "+
            object.column.AMOUNT + " INTEGER NOT NULL DEFAULT 0, "+
            object.column.TIME + " INTEGER, "+
            object.column.STATUS+" INTEGER NOT NULL DEFAULT 0, "+
            object.column.TIMEPAID+ " INTEGER)";                                             //unpaid=0,paid=1;
    private static final String SQL_DELETE= "DROP TABLE IF EXISTS "+object.column.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
