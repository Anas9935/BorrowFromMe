package com.example.anasamin.borrowfromme;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anasamin.borrowfromme.data.object;

public class MainActivity extends AppCompatActivity   {
    FloatingActionButton button;
    Boolean isFABopen=false;
    Boolean isfirstOpen=true;
    FloatingActionButton bun1;
    FloatingActionButton bun2;
    objectCursorAdapter madapter;
    String name="other";
    FrameLayout flover,root;
    EditText et;
    Button bn;

    SharedPreferences preferences;

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
        flover=(FrameLayout)findViewById(R.id.overlayout);

        root=(FrameLayout)findViewById(R.id.root);
        et=(EditText)findViewById(R.id.tev);
        bn=(Button)findViewById(R.id.save);

        final Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        preferences=PreferenceManager.getDefaultSharedPreferences(this);
        Boolean firstOpened=preferences.getBoolean("first_opened",true);
        final String n="other";
        if(firstOpened){
            root.setVisibility(View.VISIBLE);
            final SharedPreferences.Editor editor=preferences.edit();
            bn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name=et.getText().toString();
                    root.setVisibility(View.INVISIBLE);
                    editor.putString("name_key",name);
                    editor.apply();
                    intent.putExtra("NAME",name);
                    isfirstOpen=false;
                    editor.putBoolean("first_opened",false);
                    editor.apply();
                }
            });
        }
        else{
            name=preferences.getString("name_key",n);
            intent.putExtra("NAME",name);
            isfirstOpen=false;
        }


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFABopen&&!isfirstOpen) {
                        flover.setVisibility(View.VISIBLE);
                        showFABmenu();
                    } else {
                        closeFABmenu();
                    }
                }
            });
            bun1.setOnClickListener(new View.OnClickListener() {            //for giving
                @Override
                public void onClick(View v) {
                    closeFABmenu();
                    intent.putExtra("OPTION", 1);
                    startActivity(intent);
                }
            });
            bun2.setOnClickListener(new View.OnClickListener() {            //forborrowing
                @Override
                public void onClick(View v) {
                    closeFABmenu();
                    intent.putExtra("OPTION", 2);
                    startActivity(intent);
                }
            });

            flover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    closeFABmenu();
                }
            });

            display();
            madapter.notifyDataSetChanged();
        }


        public void display(){
            String[] projections={object.column.Column_ID, object.column.FROM, object.column.TO, object.column.AMOUNT, object.column.TIME, object.column.STATUS,object.column.TIMEPAID};
            String selection=object.column.STATUS+"=?";
            String selectionArgs[]={"0"};
            Cursor cursor=getContentResolver().query(object.column.CONTENT_URI,projections,selection,selectionArgs,null);
            ListView lv=(ListView)findViewById(R.id.lvXML);
            madapter=new objectCursorAdapter(this,cursor);
            lv.setAdapter(madapter);
        }
    @Override
    protected void onStart() {

        super.onStart();
        display();
        madapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        display();
        madapter.notifyDataSetChanged();
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
        l1.animate().translationY(-200).setDuration(150);
        l2.animate().translationY(-400).setDuration(200);
        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.VISIBLE);
        OvershootInterpolator interpolator=new OvershootInterpolator(10.0F);
        ViewCompat.animate(button).rotation(135.0F).withLayer().setDuration(300).setInterpolator(interpolator).start();
       // button.setImageDrawable(getResources().getDrawable(R.drawable.cross));

    }
    public void closeFABmenu(){
        isFABopen=false;
        flover.setVisibility(View.INVISIBLE);
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
            AlertDialog alertDialog=new AlertDialog.Builder(this).setMessage("Do you want to exit application")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }else{
            closeFABmenu();
        }
    }

}
