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
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class home extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    myDatabseHelper myDB;
    ArrayList<String> event_id,event_title,event_people,event_date,event_time,event_location,event_description;
    CustomAdapter customAdapter;
    Toolbar toolbar;
    ImageView empty_imgView;
    TextView empty_textView;
    ArrayList<String> original_event_id, original_event_title, original_event_people, original_event_date, original_event_time, original_event_location, original_event_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.recyclerView);
        add_button=findViewById(R.id.add_button);
        empty_imgView=findViewById(R.id.empty_imgView);
        empty_textView=findViewById(R.id.empty_textView);
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
           empty_imgView.setVisibility(View.VISIBLE);
           empty_textView.setVisibility(View.VISIBLE);
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
            // Store the original order
            original_event_id = new ArrayList<>(event_id);
            original_event_title = new ArrayList<>(event_title);
            original_event_people = new ArrayList<>(event_people);
            original_event_date = new ArrayList<>(event_date);
            original_event_time = new ArrayList<>(event_time);
            original_event_location = new ArrayList<>(event_location);
            original_event_description = new ArrayList<>(event_description);

            empty_imgView.setVisibility(View.GONE);
            empty_textView.setVisibility(View.GONE);

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
//        switch (item.getItemId()) {
//            case R.id.delete_all:
//                confirmDialog();
//                break;
//            case R.id.sort_newest:
//                sortByNewest();
//                break;
//            case R.id.sort_oldest:
//                sortByOldest();
//                break;
//            case R.id.sort_date_asc:
//                sortByDateAscending();
//                break;
//            case R.id.sort_date_desc:
//                sortByDateDescending();
//                break;
        if (item.getItemId()==R.id.delete_all){
            confirmDialog();
            return true;
        }
        else if (item.getItemId()==R.id.sort_newest){
            sortByNewest();
            return true;
        }
        else if (item.getItemId()==R.id.sort_oldest){
            sortByOldest();
            return true;
        }
        else if (item.getItemId()==R.id.sort_date_asc){
            sortByDateAscending();
            return true;
        }
        else if (item.getItemId()==R.id.sort_date_desc){
            sortByDateDescending();
            return true;
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
    // Sort by newest (assuming data is added in order)
    void sortByNewest() {
        Collections.reverse(event_id);
        Collections.reverse(event_title);
        Collections.reverse(event_people);
        Collections.reverse(event_date);
        Collections.reverse(event_time);
        Collections.reverse(event_location);
        Collections.reverse(event_description);
        customAdapter.notifyDataSetChanged();
    }

    // Sort by oldest
    void sortByOldest() {
        // Reset the lists to their original order
        event_id.clear();
        event_id.addAll(original_event_id);

        event_title.clear();
        event_title.addAll(original_event_title);

        event_people.clear();
        event_people.addAll(original_event_people);

        event_date.clear();
        event_date.addAll(original_event_date);

        event_time.clear();
        event_time.addAll(original_event_time);

        event_location.clear();
        event_location.addAll(original_event_location);

        event_description.clear();
        event_description.addAll(original_event_description);

        customAdapter.notifyDataSetChanged();
    }

    // Sort by date ascending
    void sortByDateAscending() {

    }

    // Sort by date descending
    void sortByDateDescending() {

    }

}