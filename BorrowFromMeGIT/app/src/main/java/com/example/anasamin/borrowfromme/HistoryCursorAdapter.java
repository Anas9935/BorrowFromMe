package com.example.anasamin.borrowfromme;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
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
        return LayoutInflater.from(context).inflate(R.layout.item_history,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView borr_by=(TextView)view.findViewById(R.id.BorrowerXML2);    //bto
        TextView borr_from=(TextView)view.findViewById(R.id.Borrowed_FromXML2);              //bfrom
        TextView amount=(TextView)view.findViewById(R.id.amountXML2);
        TextView time=(TextView)view.findViewById(R.id.timeXML2);
        TextView paidBTN=(TextView)view.findViewById(R.id.paid);
        TextView time_paid=(TextView)view.findViewById(R.id.timePaidXML2);

        int statusindex=cursor.getColumnIndex(object.column.STATUS);
        int namebbyindex=cursor.getColumnIndex(object.column.TO);
        int namebfmindex=cursor.getColumnIndex(object.column.FROM);
        int amt=cursor.getColumnIndex(object.column.AMOUNT);
        int tme=cursor.getColumnIndex(object.column.TIME);
        int tmePaidindex=cursor.getColumnIndex(object.column.TIMEPAID);

        int status=cursor.getInt(statusindex);
        borr_by.setText(cursor.getString(namebbyindex));
        borr_from.setText(cursor.getString(namebfmindex));
        amount.setText(String.valueOf(cursor.getInt(amt)));
        time.setText(getTime(cursor.getInt(tme)));

        if(cursor.getInt(tmePaidindex)==0){
            time_paid.setText("NOT YET PAID");
        }
        else{
            time_paid.setText(getTime(cursor.getInt(tmePaidindex)));
        }

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
    private String getTime(int unix){
        Date d=new Date(unix*1000L);
        return new SimpleDateFormat("dd/MMM/yyyy").format(d);
    }
}
