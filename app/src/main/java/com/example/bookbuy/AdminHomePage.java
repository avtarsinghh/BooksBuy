package com.example.bookbuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminHomePage extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    FloatingActionButton actionButton;
    FirebaseAuth firebaseAuth;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        recyclerView = findViewById(R.id.recyclerView);
        actionButton = findViewById(R.id.floatingButtonAdmin);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        linearLayout = findViewById(R.id.linearLayout);



        firestore.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Books>  books = new ArrayList<>();
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
                    book.setImage(""+snapshot.getData().get("image"));
                    books.add(book);
                }
                RecyclerViewAdapterAdminHome adapterAdminHome = new RecyclerViewAdapterAdminHome(
                        AdminHomePage.this, books, linearLayout
                );
                recyclerView.setAdapter(adapterAdminHome);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddModifyBook.class);
                intent.putExtra("mode", "a");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logOut : firebaseAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
