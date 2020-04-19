package com.example.bookbuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserHomePage extends AppCompatActivity {

    RecyclerView recyclerViewUh;
    FirebaseFirestore firestoreuh;
   // Toolbar toolbar;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        recyclerViewUh = findViewById(R.id.recyclerview );
        firestoreuh = FirebaseFirestore.getInstance();

       // toolbar =findViewById( R.id.menuToolbar );
      //  setSupportActionBar( toolbar );

        retrofit = RetrofitInstance.getRetrofitInstance();

        DataService dataService = retrofit.create(DataService.class);
        Call<ArrayList<Book>> call = dataService.getBooks();
        call.enqueue(new Callback<ArrayList<Book>>() {
            @Override
            public void onResponse(Call<ArrayList<Book>> call, Response<ArrayList<Book>> response) {
                ArrayList<Book>  books = new ArrayList<>();
                Log.i("body", ""+response.body());
                for(Book book : response.body()){
                    books.add(book);
                }
                BooksAdapter booksAdapter = new BooksAdapter( UserHomePage.this, books);
                recyclerViewUh.setAdapter(booksAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewUh.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<ArrayList<Book>> call, Throwable t)
            {
                Log.i("body", t.getMessage());
            }
        });

      /*  firestoreuh.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Book> books = new ArrayList<>();
                for(QueryDocumentSnapshot snapshot : task.getResult()){

                    Book book = new Book();
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
                BooksAdapter booksAdapter = new BooksAdapter(getApplicationContext(), books);
                recyclerViewUh.setAdapter( booksAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewUh.setLayoutManager(layoutManager);
            }
        });*/

  }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate( R.menu.menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(UserHomePage.this, Login.class);
                startActivity(intent);
        }

        return true;
    }
}
