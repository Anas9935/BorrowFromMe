package com.example.anasamin.borrowfromme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.anasamin.borrowfromme.data.object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class objectCursorAdapter extends CursorAdapter {
    public objectCursorAdapter(Context context, Cursor c){
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView borr_by=(TextView)view.findViewById(R.id.BorrowerXML);    //bto
        TextView borr_from=(TextView)view.findViewById(R.id.Borrowed_FromXML);              //bfrom
        TextView amount=(TextView)view.findViewById(R.id.AmountXML);
        TextView time=(TextView)view.findViewById(R.id.timeXML);
        TextView paidBTN=(TextView)view.findViewById(R.id.paid_button);

        int idindex=cursor.getColumnIndex(object.column.Column_ID);
        final String id=String.valueOf(cursor.getInt(idindex));

        int namebbyindex=cursor.getColumnIndex(object.column.TO);
        int namebfmindex=cursor.getColumnIndex(object.column.FROM);
        int amt=cursor.getColumnIndex(object.column.AMOUNT);
        int tme=cursor.getColumnIndex(object.column.TIME);

        borr_by.setText(cursor.getString(namebbyindex));
        borr_from.setText(cursor.getString(namebfmindex));
        amount.setText(String.valueOf(cursor.getInt(amt)));
        time.setText(getTime(cursor.getInt(tme)));

        paidBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long timepaid=System.currentTimeMillis()/1000L;
                ContentValues values=new ContentValues();
                values.put(object.column.STATUS,1);
                values.put(object.column.TIMEPAID,timepaid);
                String selection=object.column.Column_ID+"=?";
                String [] selectionArgs={id};
                int id=context.getContentResolver().update(object.column.CONTENT_URI,values,selection,selectionArgs);
               if(context instanceof MainActivity){
                   ((MainActivity) context).display();
               }

                //TODO 1:solve the refreshing part
            }
        });
    }
    private String getTime(int unix){
        Date d=new Date(unix*1000L);
        return new SimpleDateFormat("EEE, dd/MMM/yyyy   hh:mma ").format(d);
    }
}
