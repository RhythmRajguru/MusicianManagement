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
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;


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
        toolbar.setTitle("Recent Events");
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
        else if (item.getItemId()==R.id.logout){
            logout();
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
    void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),activity_login.class));
        finish();
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
        Toast.makeText(this, "Data sorted by newest", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Data sorted by oldest", Toast.LENGTH_SHORT).show();
    }

    // Sort by date ascending
    void sortByDateAscending() {
        sortEventsByDate(true);
        Toast.makeText(this, "Data sorted by Date in ascending", Toast.LENGTH_SHORT).show();
    }

    // Sort by date descending
    void sortByDateDescending() {
        sortEventsByDate(false);
        Toast.makeText(this, "Data sorted by Date in Descending", Toast.LENGTH_SHORT).show();
    }
    // Helper method to sort events by date
    private void sortEventsByDate(boolean ascending) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Create a list of events to sort
        ArrayList<Event> events = new ArrayList<>();
        for (int i = 0; i < event_id.size(); i++) {
            try {
                Date date = dateFormat.parse(event_date.get(i));
                events.add(new Event(
                        event_id.get(i),
                        event_title.get(i),
                        event_people.get(i),
                        date, // Store date for sorting
                        event_time.get(i),
                        event_location.get(i),
                        event_description.get(i)
                ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Sort the events based on date
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                if (ascending) {
                    return e1.getDate().compareTo(e2.getDate());
                } else {
                    return e2.getDate().compareTo(e1.getDate());
                }
            }
        });

        // Clear existing data and add sorted data back to the lists
        event_id.clear();
        event_title.clear();
        event_people.clear();
        event_date.clear();
        event_time.clear();
        event_location.clear();
        event_description.clear();

        for (Event event : events) {
            event_id.add(event.getId());
            event_title.add(event.getTitle());
            event_people.add(event.getPeople());
            event_date.add(dateFormat.format(event.getDate()));
            event_time.add(event.getTime());
            event_location.add(event.getLocation());
            event_description.add(event.getDescription());
        }

        // Notify the adapter of the updated data
        customAdapter.notifyDataSetChanged();
    }

    // Event class to hold event details
    private class Event {
        private String id;
        private String title;
        private String people;
        private Date date;
        private String time;
        private String location;
        private String description;

        public Event(String id, String title, String people, Date date, String time, String location, String description) {
            this.id = id;
            this.title = title;
            this.people = people;
            this.date = date;
            this.time = time;
            this.location = location;
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getPeople() {
            return people;
        }

        public Date getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getLocation() {
            return location;
        }

        public String getDescription() {
            return description;
        }
}}