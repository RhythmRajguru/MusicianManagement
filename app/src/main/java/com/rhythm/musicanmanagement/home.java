package com.rhythm.musicanmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class home extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    myDatabseHelper myDB;
    ArrayList<String> event_id,event_title,event_people,event_date,event_time,event_location,event_description;
    CustomAdapter customAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.recyclerView);
        add_button=findViewById(R.id.add_button);
        //toolbar added
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), addEvents.class);
                startActivity(intent);
            }
        });
    myDB=new myDatabseHelper(getApplicationContext());
    event_id=new ArrayList<>();
    event_title=new ArrayList<>();
    event_people=new ArrayList<>();
    event_date=new ArrayList<>();
    event_time=new ArrayList<>();
    event_location=new ArrayList<>();
    event_description=new ArrayList<>();
    storedataInArrays();
    customAdapter=new CustomAdapter(home.this,getApplicationContext(),event_id,event_title,event_people,event_date,event_time,event_location,event_description);
    recyclerView.setAdapter(customAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(home.this));

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Reload data when the activity is resumed
        storedataInArrays();

        // Notify the adapter of the updated data
        customAdapter.notifyDataSetChanged();
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    void storedataInArrays(){
        event_id.clear();
        event_title.clear();
        event_people.clear();
        event_date.clear();
        event_time.clear();
        event_location.clear();
        event_description.clear();

        Cursor cursor= myDB.readallData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "There is no data..", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                event_id.add(cursor.getString(0));
                event_title.add(cursor.getString(1));
                event_people.add(cursor.getString(2));
                event_date.add(cursor.getString(3));
                event_time.add(cursor.getString(4));
                event_location.add(cursor.getString(5));
                event_description.add(cursor.getString(6));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.data_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete Data?");
        builder.setMessage("Are you sure you want to delete all data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDatabseHelper myDB=new myDatabseHelper(home.this);
                myDB.deleteAllData();
                recreate();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
}