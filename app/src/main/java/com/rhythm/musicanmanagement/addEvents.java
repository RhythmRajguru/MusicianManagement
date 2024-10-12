package com.rhythm.musicanmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class addEvents extends AppCompatActivity {
    EditText etTitleName,etPeopleCount,etDate,etTime,etLocation,etDescription;
    Button add_databtn;
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

        add_databtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabseHelper myDB=new myDatabseHelper(addEvents.this);
                try {
                    myDB.addEvent(etTitleName.getText().toString().trim(),Integer.valueOf(etPeopleCount.getText().toString().trim()),
                            etDate.getText().toString().trim(),etTime.getText().toString().trim(),
                            etLocation.getText().toString().trim(),etDescription.getText().toString().trim());
                    etTitleName.setText("");
                    etPeopleCount.setText("");
                    etDate.setText("");
                    etTime.setText("");
                    etLocation.setText("");
                    etDescription.setText("");
                }
                catch (Exception e){
                    e.getMessage();
                }

            }
        });

    }
}