package com.example.anasamin.borrowfromme;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.anasamin.borrowfromme.data.object;

public class MainActivity extends AppCompatActivity   {
    FloatingActionButton button;
    Boolean isFABopen=false;
    FloatingActionButton bun1;
    FloatingActionButton bun2;
    objectCursorAdapter madapter;
    String name="Anas Amin";

    LinearLayout l1;
    LinearLayout l2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(FloatingActionButton)findViewById(R.id.FAB);
        bun1=(FloatingActionButton)findViewById(R.id.FAB1);
        bun2=(FloatingActionButton)findViewById(R.id.FAB2);

        l1=(LinearLayout)findViewById(R.id.layoutFAB1);
        l2=(LinearLayout)findViewById(R.id.layoutFAB2);

        final Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        intent.putExtra("NAME",name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABopen){
                    showFABmenu();
                }
                else { closeFABmenu(); }
            }
        });
        bun1.setOnClickListener(new View.OnClickListener() {            //for giving
            @Override
            public void onClick(View v) {
                closeFABmenu();
                intent.putExtra("OPTION",1);
                startActivity(intent);
            }
        });
        bun2.setOnClickListener(new View.OnClickListener() {            //forborrowing
            @Override
            public void onClick(View v) {
                closeFABmenu();
                intent.putExtra("OPTION",2);
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
    public void showFABmenu(){
        isFABopen=true;
        l1.animate().translationY(-200);
        l2.animate().translationY(-400);
        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.VISIBLE);
        OvershootInterpolator interpolator=new OvershootInterpolator(10.0F);
        ViewCompat.animate(button).rotation(135.0F).withLayer().setDuration(300).setInterpolator(interpolator).start();
       // button.setImageDrawable(getResources().getDrawable(R.drawable.cross));
    }
    public void closeFABmenu(){
        isFABopen=false;
        l1.animate().translationY(0);
        l1.animate().translationY(0);
        l1.setVisibility(View.INVISIBLE);
        l2.setVisibility(View.INVISIBLE);
        OvershootInterpolator interpolator=new OvershootInterpolator(10.0F);
        ViewCompat.animate(button).rotation(0.0F).withLayer().setDuration(300).setInterpolator(interpolator).start();
    }
    @Override
    public void onBackPressed() {
        if(!isFABopen){
            super.onBackPressed();
        }else{
            closeFABmenu();
        }
    }
}
