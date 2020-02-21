package com.example.bookbuy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.TreeMap;

public class Login extends AppCompatActivity {

    EditText emailEt,passwordEt;
    Button login;
    TextView signUp;
    private FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
        db = FirebaseFirestore.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(Login.this, "You are logged in ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this,UserHomePage.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(Login.this, "Please Login ", Toast.LENGTH_SHORT).show();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                if (email.isEmpty()) {
                        emailEt.setError("Please enter email id");
                        emailEt.requestFocus();
                    } else if (password.isEmpty()) {
                        passwordEt.setError("Please enter your password");
                        passwordEt.requestFocus();
                    } else if (email.isEmpty() && password.isEmpty()) {
                        Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    } else if (!(email.isEmpty() && password.isEmpty())) {
                        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login Unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                                } else {
                                    FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
                                    Intent intent = new Intent(Login.this, UserHomePage.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Login.this, "Error occured!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        }
    }
