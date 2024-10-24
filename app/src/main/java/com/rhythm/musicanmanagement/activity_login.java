package com.rhythm.musicanmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class activity_login extends AppCompatActivity {
    TextView txt_signUp,reset_password;
    EditText etEmail,etPassword;
    Button btn_signIn;
    FirebaseAuth fAuth;
    Toolbar toolbar;
    private GoogleSignInClient mGoogleSignInClient; // GoogleSignInClient instance
    private static final int RC_SIGN_IN = 101; // Request code for Google sign-in

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            if (currentUser.getEmail() != null) {
                // User is signed in with email/password
                String email = currentUser.getEmail();
                String name=currentUser.getDisplayName();
                Toast.makeText(this, "Welcome, " + name, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),home.class));
                finish();
            }
        } else {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_signUp=findViewById(R.id.txt_signUp);
        reset_password=findViewById(R.id.reset_password);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btn_signIn=findViewById(R.id.btn_signIn);
//toolbar added
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sign in");

        fAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Get this from strings.xml
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Google Sign-In Button click listener
        Button googleSignInButton = findViewById(R.id.googlebtn_signIn);
        googleSignInButton.setOnClickListener(v -> signIn());

        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), activity_register.class));
                finish();
            }
        });






        //reset password
        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your email to receive reset link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            String mail=resetMail.getText().toString().trim();
                            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(activity_login.this, "Reset link send to your email.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(activity_login.this, "Error! Reset link is not sent" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

        //sign in button
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etEmail.getText().toString().trim();
                String password=etPassword.getText().toString().trim();

               if(email.isEmpty()){
                    etEmail.requestFocus();
                    etEmail.setError("Please enter your name");
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    etEmail.requestFocus();
                    etEmail.setError("Please enter correct email");
                    return;
                }
                else if(password.isEmpty()){
                    etPassword.requestFocus();
                    etPassword.setError("Please enter your password");
                    return;
                }


                else if(password.length() <8){
                    etPassword.requestFocus();
                    etPassword.setError("Min 8 characters required");
                    return;
                }

                else {
                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(getApplicationContext(), home.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                Toast.makeText(activity_login.this, "LoggedIn Successfully..", Toast.LENGTH_SHORT).show();
                                etEmail.setText("");
                                etPassword.setText("");
                                finish();
                            } else {
                                etEmail.setText("");
                                etPassword.setText("");
                                Toast.makeText(getApplicationContext(), "Invalid email/password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
            }
        });

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("Google SignIn", "Google sign in failed with code: " + e.getStatusCode());
                Toast.makeText(this, "Google Sign In Failed: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = fAuth.getCurrentUser();
                        Toast.makeText(activity_login.this, "Welcome, " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        // Navigate to home activity
                        Intent i = new Intent(getApplicationContext(), home.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(activity_login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
   }