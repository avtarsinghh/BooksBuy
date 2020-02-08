package com.example.bookbuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHomePage extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        recyclerView = findViewById(R.id.recyclerView);
        firestore = FirebaseFirestore.getInstance();
    }
}
