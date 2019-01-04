package com.example.anasamin.borrowfromme;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anasamin.borrowfromme.data.object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class detail_fragment extends Fragment {
    Cursor cursor;
    FragmentManager fm2;
    Fragment frag;
    FrameLayout frame;
    TextView date;
    public detail_fragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_detail,container,false);

        TextView borr_by=(TextView)root.findViewById(R.id.BorrowerXML2);    //bto
        TextView borr_from=(TextView)root.findViewById(R.id.Borrowed_FromXML2);              //bfrom
        TextView amount=(TextView)root.findViewById(R.id.amountXML2);
        TextView time=(TextView)root.findViewById(R.id.timeXML2);
        TextView paidBTN=(TextView)root.findViewById(R.id.paid);
        TextView time_paid=(TextView)root.findViewById(R.id.timePaidXML2);
        ImageView cross=(ImageView)root.findViewById(R.id.crossXML);
        date=(TextView)root.findViewById(R.id.datepay);

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

        if(cursor.getInt(tmePaidindex)!=0){
            time_paid.setText(getTime(cursor.getInt(tmePaidindex)));
        }
        else{
            time_paid.setText("");
            date.setText("");
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
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    fm2.beginTransaction().remove(frag).commit();
                    frame.setVisibility(View.INVISIBLE);
            }
        });

return root;
    }
    private String getTime(int unix){
        Date d=new Date(unix*1000L);
        return new SimpleDateFormat("dd/MMM/yyyy").format(d);
    }
    public void setCursor(Cursor c){
        this.cursor=c;
    }
    public void getFm(FragmentManager fm,Fragment fragment){
        this.fm2=fm;
        this.frag=fragment;
    }
    public void setfl(FrameLayout fl){
        this.frame=fl;
    }

    }
