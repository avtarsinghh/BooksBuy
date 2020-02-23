package com.example.bookbuy;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText nameEt, emailSignUp, passwordSignUp, confirmPassword;
    Button submit;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private static final String TAG = "EmailPassword";
    String tutorialsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        nameEt = findViewById(R.id.name);
        emailSignUp = findViewById(R.id.emailSignUp);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        confirmPassword = findViewById(R.id.confirmPassword);
        submit = findViewById(R.id.submit);



        //creating an instamce for database
        db = FirebaseFirestore.getInstance();
        //creating an instance for firebase authentication
        mAuth = FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEt.getText().toString();
                final String email = emailSignUp.getText().toString();
                String password = passwordSignUp.getText().toString();
                String confirmPswd = confirmPassword.getText().toString();
                if (!password.equals(confirmPswd)) {
                    Toast.makeText(SignUp.this, "Password does not match", Toast.LENGTH_SHORT).show();
                } else {
                    if (email.isEmpty()) {
                        emailSignUp.setError("Please enter email id");
                        emailSignUp.requestFocus();
                    } else if (password.isEmpty()) {
                        passwordSignUp.setError("Please enter your password");
                        passwordSignUp.requestFocus();
                    } else if (name.isEmpty()) {
                        nameEt.setError("Please enter your name");
                        nameEt.requestFocus();
                    } else if (email.isEmpty() && password.isEmpty()) {
                        Toast.makeText(SignUp.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    } else if (!(email.isEmpty() && password.isEmpty())) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.i("taskError", task.getException().getMessage());
                                    Toast.makeText(SignUp.this, "Signup Unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                                } else {

                                    //add new user to database
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("name",name);
                                    user.put("email",email);
                                    user.put("isAdmin", false);

                                    FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();

                                    db.collection("users").document(userFb.getUid())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(SignUp.this, "Submitted", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(SignUp.this, Login.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("error",e.getMessage());
                                                }
                                            });

                                }
                            }
                        });
                    } else {
                        Toast.makeText(SignUp.this, "Error occured!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}
