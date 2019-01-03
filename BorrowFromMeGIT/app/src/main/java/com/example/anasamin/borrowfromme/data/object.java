package com.example.anasamin.borrowfromme.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class object {
    object(){}
    public static final String CONTENT_AUTHORITY="com.android.anasamin.borrowfromme";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH="Table_Name";

    public static class column implements BaseColumns{
        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH);
        public static final String CONTENT_LIST_TYPE=ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH;
        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH;
        public static final String TABLE_NAME="Table_Name";
        public static final String Column_ID="_id";
        public static final String FROM="Bfrom";
        public static final String TO="Bto";
        public static final String AMOUNT="Amount";
        public static final String TIME="time";
        public static final String STATUS="status";
        public static final String TIMEPAID="timePaid";
    }
}
