package com.example.anasamin.borrowfromme;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.anasamin.borrowfromme.data.object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryCursorAdapter extends CursorAdapter {



    public HistoryCursorAdapter(Context context,Cursor c){
    super(context,c,0);
}

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_history_small,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView borr_by=(TextView)view.findViewById(R.id.BorrowerXML2);    //bto
        TextView paidBTN=(TextView)view.findViewById(R.id.paid);

        int statusindex=cursor.getColumnIndex(object.column.STATUS);
        int namebbyindex=cursor.getColumnIndex(object.column.TO);

        int status=cursor.getInt(statusindex);
        borr_by.setText(cursor.getString(namebbyindex));


        switch(status){
            case 0:{paidBTN.setText("UNPAID");
                paidBTN.setTextColor(Color.parseColor("#CC2200"));
                break;
            }
            case 1:{paidBTN.setText("PAID");
                paidBTN.setTextColor(Color.parseColor("#000000"));
                break;
            }
        }

    }
    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }
}
