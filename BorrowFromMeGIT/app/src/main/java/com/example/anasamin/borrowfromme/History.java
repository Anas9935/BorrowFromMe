package com.example.anasamin.borrowfromme;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        String sortOrder=object.column.TIME+" DESC";
        Cursor cursor=getContentResolver().query(object.column.CONTENT_URI,projections,null,null,sortOrder);
        ListView lv=(ListView)findViewById(R.id.lv2XML);
        madapter=new HistoryCursorAdapter(this,cursor);
        lv.setAdapter(madapter);
        madapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.settings_main,menu);
        return true;
    }
    public void delete_paid(){
        String selection=object.column.STATUS+"=?";
        String[] selectionArgs={"1"};
        int id=getContentResolver().delete(object.column.CONTENT_URI,selection,selectionArgs);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_action:{
                delete_paid();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
