package com.rhythm.musicanmanagement;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class update_event extends AppCompatActivity {
    EditText etTitleName,etPeopleCount,etDate,etTime,etLocation,etDescription;
    Button update_databtn,btn_delete;
    String id,title,people,date,time,location,description;
    Toolbar toolbar;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        etTitleName=findViewById(R.id.etTitleName);
        etPeopleCount=findViewById(R.id.etPeopleCount);
        etDate=findViewById(R.id.etDate);
        etTime=findViewById(R.id.etTime);
        etLocation=findViewById(R.id.etLocation);
        etDescription=findViewById(R.id.etDescription);

        update_databtn=findViewById(R.id.update_databtn);
        btn_delete=findViewById(R.id.btn_delete);

        //toolbar added
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Update Event");

        getandSetIntentData();
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Toast.makeText(update_event.this, "Ad loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adView.loadAd(adRequest);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked();
            }
        });

        update_databtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabseHelper myDB=new myDatabseHelper(update_event.this);
                title=etTitleName.getText().toString().trim();
                people=etPeopleCount.getText().toString().trim();
                date=etDate.getText().toString().trim();
                time=etTime.getText().toString().trim();
                location=etLocation.getText().toString().trim();
                description=etDescription.getText().toString().trim();
                myDB.updateData(id,title,people,date,time,location,description);

                finish();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               confirmDialog();

            }
        });



    }
    void getandSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("people")
                && getIntent().hasExtra("date") && getIntent().hasExtra("time") && getIntent().hasExtra("location")
                && getIntent().hasExtra("description")){
            //getting data
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
            people=getIntent().getStringExtra("people");
            date=getIntent().getStringExtra("date");
            time=getIntent().getStringExtra("time");
            location=getIntent().getStringExtra("location");
            description=getIntent().getStringExtra("description");

            //setting data
            etTitleName.setText(title);
            etPeopleCount.setText(people);
            etDate.setText(date);
            etTime.setText(time);
            etLocation.setText(location);
            etDescription.setText(description);
        }
        else{
            Toast.makeText(this, "No Data....", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ title + " ?");
        builder.setMessage("Are you sure you want to delete "+ title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDatabseHelper myDB=new myDatabseHelper(update_event.this);
                myDB.deleteOnerow(id);
                finish();
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