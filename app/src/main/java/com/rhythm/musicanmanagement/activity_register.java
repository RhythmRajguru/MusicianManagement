package com.rhythm.musicanmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_register extends AppCompatActivity {
    Toolbar toolbar;
    EditText etName, etEmail, etPassword;
    Button btn_signUp;
    TextView txt_login;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //toolbar intialization
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Register");

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btn_signUp = findViewById(R.id.btn_signUp);
        txt_login = findViewById(R.id.txt_login);


        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), activity_login.class));
                finish();
            }
        });


        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), home.class));
            finish();
        }

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (name.isEmpty()) {
                    etName.requestFocus();
                    etName.setError("Please enter your name");
                    return;
                } else if (email.isEmpty()) {
                    etEmail.requestFocus();
                    etEmail.setError("Please enter your name");
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.requestFocus();
                    etEmail.setError("Please enter correct email");
                    return;
                } else if (password.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError("Please enter your password");
                    return;
                } else if (password.length() < 8) {
                    etPassword.requestFocus();
                    etPassword.setError("Min 8 characters required");
                    return;
                } else {

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                etName.setText("");
                                etEmail.setText("");
                                etPassword.setText("");
                                Toast.makeText(activity_register.this, "Registered Successully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity_register.this, home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                etName.setText("");
                                etEmail.setText("");
                                etPassword.setText("");
                                Toast.makeText(activity_register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}