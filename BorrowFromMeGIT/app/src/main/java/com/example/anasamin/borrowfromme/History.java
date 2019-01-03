package com.example.anasamin.borrowfromme;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.anasamin.borrowfromme.data.object;

public class History extends AppCompatActivity {
    HistoryCursorAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        display();
    }
    void display(){
        String[] projections={object.column.Column_ID, object.column.FROM, object.column.TO, object.column.AMOUNT, object.column.TIME, object.column.STATUS,object.column.TIMEPAID};
        Cursor cursor=getContentResolver().query(object.column.CONTENT_URI,projections,null,null,null);
        ListView lv=(ListView)findViewById(R.id.lv2XML);
        madapter=new HistoryCursorAdapter(this,cursor);
        lv.setAdapter(madapter);
        madapter.notifyDataSetChanged();
    }
}
