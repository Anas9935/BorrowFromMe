package com.example.anasamin.borrowfromme;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anasamin.borrowfromme.data.object;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return true;
    }
public void saveindatabase(){
    TextView frm=(TextView)findViewById(R.id.edit1);
    TextView to=(TextView)findViewById(R.id.edit2);
    TextView amt=(TextView)findViewById(R.id.amount);
    int amot=Integer.parseInt(amt.getText().toString());
   // int time=1546498319;
    int status=0;
    int time_paid=0;
    Long tsLong = System.currentTimeMillis()/1000L;
    long time=tsLong;
    ContentValues values=new ContentValues();
    values.put(object.column.FROM,frm.getText().toString().trim());
    values.put(object.column.TO,to.getText().toString().trim());
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

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:{
                saveindatabase();
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
