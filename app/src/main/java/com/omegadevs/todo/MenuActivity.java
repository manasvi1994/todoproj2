package com.omegadevs.todo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    String tableName;
    SQLiteDatabase db;
    List<Tasks> taskDetails;
    RecyclerView rv;
    Cursor c;
    String tname,taddress,ttime,tdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        tableName = bundle.getString("tablename");

        db = openOrCreateDatabase("tasks",MODE_PRIVATE,null);

        try{
            db.execSQL("create table '"+tableName+"' (tname varchar(50), taddress varchar(200), tdate varchar(20), ttime varchar(20));");
            Toast.makeText(getApplicationContext(),"table created!",Toast.LENGTH_SHORT ).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"table already exists !",Toast.LENGTH_SHORT ).show();

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addnew = new Intent(MenuActivity.this,NewTodoActivity.class);
                addnew.putExtra("tablename",tableName);
                startActivity(addnew);
                finish();

            }
        });

        rv =(RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        //try{
            rv.setLayoutManager(llm);
            rv.setHasFixedSize(true);
            initialisedata();
            final RecyclerAdapter adapter = new RecyclerAdapter(taskDetails);
            rv.setAdapter(adapter);

//        }catch (Exception e){
     //       Toast.makeText(getApplicationContext(), "Empty Adapter", Toast.LENGTH_SHORT).show();

 //       }
        }

    public void initialisedata(){

        taskDetails = new ArrayList<>();
        c = db.rawQuery("select * from '"+tableName+"';",null);
        c.moveToFirst();
        while (c.moveToNext()) {
            tname = c.getString(0);
            taddress = c.getString(1);
            ttime = c.getString(2);
            tdate = c.getString(3);
            taskDetails.add(new Tasks(tname,taddress,ttime,tdate));
            c.moveToNext();

        }
        ;

    }

}
