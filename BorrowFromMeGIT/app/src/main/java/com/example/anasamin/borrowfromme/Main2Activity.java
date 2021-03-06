package com.example.anasamin.borrowfromme;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anasamin.borrowfromme.data.object;

public class Main2Activity extends AppCompatActivity {
    EditText frm;
  //  TextView to;
    EditText amt;
    TextView borrow;
    String name;
    int opt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent=getIntent();
         name=intent.getStringExtra("NAME");
         opt=intent.getIntExtra("OPTION",0);

        borrow=(TextView)findViewById(R.id.text1);
        if(opt==1){
            borrow.setText(getString(R.string.from));
        }
        else if(opt==2){
            borrow.setText(getString(R.string.to));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return true;
    }
    public void saveindatabase(){
TextView tvwar=(TextView)findViewById(R.id.warning);
    frm=(EditText)findViewById(R.id.edit1);
    amt=(EditText)findViewById(R.id.amount);
    TextView tvwar2=(TextView)findViewById(R.id.warning2);
    if(TextUtils.isEmpty(frm.getText().toString())){                                        //for checking empty text view
        frm.setBackground(getDrawable(R.drawable.shape_red));
        tvwar.setVisibility(View.VISIBLE);
        tvwar2.setVisibility(View.INVISIBLE);
        frm.requestFocus();
    }
    else if(TextUtils.isEmpty(amt.getText().toString())){                                        //for checking empty text view
        amt.setBackground(getDrawable(R.drawable.shape_red));
        tvwar2.setVisibility(View.VISIBLE);
        tvwar.setVisibility(View.INVISIBLE);
        amt.requestFocus();
    }
    else if(TextUtils.isEmpty(frm.getText().toString())&&TextUtils.isEmpty(amt.getText().toString())){       //for checking both the text views
        frm.setBackground(getDrawable(R.drawable.shape_red));
        tvwar.setVisibility(View.VISIBLE);
        amt.setBackground(getDrawable(R.drawable.shape_red));
        tvwar2.setVisibility(View.VISIBLE);
        frm.requestFocus();
    }
    else{
        tvwar.setVisibility(View.INVISIBLE);
        tvwar2.setVisibility(View.INVISIBLE);
//       TODO  Start from here: test on the device

        int amot=Integer.parseInt(amt.getText().toString());
        int status=0;
        int time_paid=0;
        Long tsLong = System.currentTimeMillis()/1000L;
        long time=tsLong;
        ContentValues values=new ContentValues();
        if(opt==1){
            values.put(object.column.FROM,frm.getText().toString().trim());
            values.put(object.column.TO,name);
        }
        else if(opt==2){
            values.put(object.column.FROM,name);
            values.put(object.column.TO,frm.getText().toString().trim());
        }

        values.put(object.column.AMOUNT,amot);
        values.put(object.column.TIME,time);
        values.put(object.column.STATUS,status);
        values.put(object.column.TIMEPAID,time_paid);
        Uri muri=getContentResolver().insert(object.column.CONTENT_URI,values);
        if(muri==null){
            Log.e("LOGTAG://Main2Activity", "saveindatabase: Failed");
        }
        else{
            Toast.makeText(Main2Activity.this,"Saved",Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:{
                saveindatabase();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
