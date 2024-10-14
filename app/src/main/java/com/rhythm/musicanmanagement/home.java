package com.rhythm.musicanmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.recyclerView);
        add_button=findViewById(R.id.add_button);

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
}