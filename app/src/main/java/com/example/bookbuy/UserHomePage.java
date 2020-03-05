package com.example.bookbuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserHomePage extends AppCompatActivity {

    RecyclerView recyclerViewUh;
    FirebaseFirestore firestoreuh;
    Button btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        recyclerViewUh = findViewById(R.id.recyclerview );
        firestoreuh = FirebaseFirestore.getInstance();

       btnlogout=findViewById(R.id.btnLogout);

        firestoreuh.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Books> books = new ArrayList<>();
                for(QueryDocumentSnapshot snapshot : task.getResult()){

                    Books book = new Books();
                    book.setId(snapshot.getId());
                    book.setTitle(""+snapshot.getData().get("title"));
                    book.setPublisher(""+snapshot.getData().get("publisher"));
                    book.setLanguage(""+snapshot.getData().get("language"));
                    book.setEdition(""+snapshot.getData().get("edition"));
                    book.setRating(""+snapshot.getData().get("rating"));
                    book.setYop(""+snapshot.getData().get("yop"));
                    book.setDescription(""+snapshot.getData().get("description"));
                    book.setAuthor(""+snapshot.getData().get("author"));
                    book.setImage(""+snapshot.getData().get("linkToImage"));
                    books.add(book);
                }
                BooksAdapter booksAdapter = new BooksAdapter(getApplicationContext(), books
                );
                recyclerViewUh.setAdapter( booksAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewUh.setLayoutManager(layoutManager);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(UserHomePage.this,Login.class);
                startActivity(intent);
            }
        });
  }
}
