package com.example.anasamin.borrowfromme;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.anasamin.borrowfromme.data.object;

public class MainActivity extends AppCompatActivity   {
    FloatingActionButton button;
    objectCursorAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(FloatingActionButton)findViewById(R.id.FAB);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        display();
        }

        public void display(){
            String[] projections={object.column.Column_ID, object.column.FROM, object.column.TO, object.column.AMOUNT, object.column.TIME, object.column.STATUS,object.column.TIMEPAID};
            String selection=object.column.STATUS+"=?";
            String selectionArgs[]={"0"};
            Cursor cursor=getContentResolver().query(object.column.CONTENT_URI,projections,selection,selectionArgs,null);
            ListView lv=(ListView)findViewById(R.id.lvXML);
            madapter=new objectCursorAdapter(this,cursor);
            lv.setAdapter(madapter);
            madapter.notifyDataSetChanged();
        }
    @Override
    protected void onStart() {

        super.onStart();
        display();
    }

    @Override
    protected void onResume() {
        super.onResume();
        display();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.history_action:{
                startActivity(new Intent(MainActivity.this,History.class));
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

}
