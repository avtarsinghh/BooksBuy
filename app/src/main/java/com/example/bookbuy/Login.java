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
import java.util.HashMap;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText emailEt,passwordEt;
    Button login;
    TextView signUp;
    Retrofit retrofit;
    User user;
    //private FirebaseAuth mFirebaseAuth;
    //FirebaseFirestore db;
    //private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        retrofit = RetrofitInstance.getRetrofitInstance();
        DataService dataService = retrofit.create(DataService.class);

        user = new User();
        //mFirebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
        //db = FirebaseFirestore.getInstance();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(Login.this,SignUp.class);
                startActivity(intent1);
            }
        });

        /*mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(Login.this, "You are logged in ", Toast.LENGTH_SHORT).show();
                    if(mFirebaseUser.getEmail().equals("av.avtargill@gmail.com")){
                        Intent i = new Intent(Login.this,AdminHomePage.class);
                        startActivity(i);
                    }
                    else{
                        Intent i = new Intent(Login.this,UserHomePage.class);
                        startActivity(i);
                    }
                }
                else {
                    Toast.makeText(Login.this, "Please Login ", Toast.LENGTH_SHORT).show();
                }
            }
        };*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = emailEt.getText().toString();
                final String password = passwordEt.getText().toString();
                user.setPassword(password);
                user.setEmail(email);
                if (email.equals("")) {
                        emailEt.setError("Please enter email id");
                        emailEt.requestFocus();
                    } else if (password.isEmpty()) {
                        passwordEt.setError("Please enter your password");
                        passwordEt.requestFocus();
                    } else if (email.isEmpty() && password.isEmpty()) {
                        Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    } else if (!(email.isEmpty() && password.isEmpty())) {
                            Call<String> callCheck = dataService.userlogin(user);
                            callCheck.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.i("response", "" + response.body());
                                    if (response.body().equalsIgnoreCase("Valid user")) {
                                        Toast.makeText(Login.this, " Login Successful ", Toast.LENGTH_LONG).show();
                                        Intent intent;
                                        if(email.equalsIgnoreCase("avi.avtargill@gmail.com")){
                                            intent = new Intent(getApplicationContext(), AdminHomePage.class);
                                        }
                                        else{
                                            intent = new Intent(getApplicationContext(), UserHomePage.class);
                                        }
                                        startActivity(intent);
                                        finishAffinity();
                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Login.this, "Invalid Details", Toast.LENGTH_LONG).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(Login.this, "Invalid Details", Toast.LENGTH_LONG).show();
                                }
                            });
                        /*mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login Unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                                } else {if (email.equals("av.avtargill@gmail.com")&&password.equals("123456"))
                                {
                                    Intent i = new Intent(Login.this,AdminHomePage.class);
                                    startActivity(i);
                                }
                                else
                                    {
                                    Intent intent = new Intent(Login.this, UserHomePage.class);
                                    startActivity(intent);
                                }
                                }
                            }
                        });*/
                    } else {
                        Toast.makeText(Login.this, "Error occured!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        protected void onStart(){
        super.onStart();
      //  mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        }
    }
