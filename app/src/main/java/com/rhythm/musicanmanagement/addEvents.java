package com.rhythm.musicanmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.android.gms.ads.MobileAds;

public class addEvents extends AppCompatActivity {
    EditText etTitleName,etPeopleCount,etDate,etTime,etLocation,etDescription;
    Button add_databtn;
    Toolbar toolbar;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        etTitleName=findViewById(R.id.etTitleName);
        etPeopleCount=findViewById(R.id.etPeopleCount);
        etDate=findViewById(R.id.etDate);
        etTime=findViewById(R.id.etTime);
        etLocation=findViewById(R.id.etLocation);
        etDescription=findViewById(R.id.etDescription);

        add_databtn=findViewById(R.id.add_databtn);

        //toolbar added
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Data");

        //banner ad implmentation
        MobileAds.initialize(this, initializationStatus -> {
            // SDK initialized, you can load ads here
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Toast.makeText(addEvents.this, "Ad loaded", Toast.LENGTH_LONG).show();
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

         add_databtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=etTitleName.getText().toString();
                String people=etPeopleCount.getText().toString();
                String date=etDate.getText().toString();
                String time=etTime.getText().toString();
                String location=etLocation.getText().toString();
                String description=etDescription.getText().toString();

                if(title.isEmpty()){
                    etTitleName.requestFocus();
                    etTitleName.setError("Please enter Title name");
                    return;
                }else if(people.isEmpty()){
                    etPeopleCount.requestFocus();
                    etPeopleCount.setError("Please enter People count");
                    return;
                }
                else if(date.isEmpty())
                {
                    etDate.requestFocus();
                    etDate.setError("Please enter date");
                    return;
                }
                else if(time.isEmpty()){
                    etTime.requestFocus();
                    etTime.setError("Please enter time");
                    return;
                }


                else if(location.isEmpty()){
                    etLocation.requestFocus();
                    etLocation.setError("Please enter location");
                    return;
                }
                else if(description.isEmpty()){
                    etDescription.requestFocus();
                    etDescription.setError("Please enter description");
                    return;
                }
                else {
                    myDatabseHelper myDB = new myDatabseHelper(addEvents.this);

                    myDB.addEvent(etTitleName.getText().toString().trim(), Integer.valueOf(etPeopleCount.getText().toString().trim()),
                            etDate.getText().toString().trim(), etTime.getText().toString().trim(),
                            etLocation.getText().toString().trim(), etDescription.getText().toString().trim());
                    etTitleName.setText("");
                    etPeopleCount.setText("");
                    etDate.setText("");
                    etTime.setText("");
                    etLocation.setText("");
                    etDescription.setText("");

                    finish();
                }

            }
        });



    }
}