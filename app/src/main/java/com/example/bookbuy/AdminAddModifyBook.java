package com.example.bookbuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminAddModifyBook extends AppCompatActivity {
    Intent intent;
    String mode;
    Button addModifyBtn;
    Book book;
    Retrofit retrofit;
    EditText idET, titleET, authorET, publisherET, languageET, editionET, ratingET, yopET, descriptionET, linkImageET;
    FirebaseFirestore firestore;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_modify_book);
        getIds();
        retrofit = RetrofitInstance.getRetrofitInstance();
        DataService dataService = retrofit.create(DataService.class);

        intent = getIntent();
        mode = intent.getStringExtra("mode");
        findAddOrModify();
        addModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditTextData();
                Call<String> call1 = dataService.addModifyBook(book);
                Call<String> callCheck = dataService.getBook(book);

                callCheck.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.i("response", ""+ response.body());
                        if (response.body().equalsIgnoreCase("Exist") && mode.equalsIgnoreCase("a")){
                            Toast.makeText(AdminAddModifyBook.this, "This Id for book already exists", Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (verifyData()) {
                                linearLayout.setVisibility(View.VISIBLE);
                                call1.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        linearLayout.setVisibility(View.GONE);
                                        Toast.makeText(AdminAddModifyBook.this, "Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), AdminHomePage.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(AdminAddModifyBook.this, "Failed!!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(AdminAddModifyBook.this, "Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });
                /*firestore.collection("books").document(book.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists() && mode.equalsIgnoreCase("a")){
                            Toast.makeText(AdminAddModifyBook.this, "This Id for book already exists", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if (verifyData()) {
                                linearLayout.setVisibility(View.VISIBLE);
                                firestore.collection("books").document(book.getId()).set(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        linearLayout.setVisibility(View.GONE);
                                        Intent intent = new Intent(getApplicationContext(), AdminHomePage.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }
                                });
                            }
                        }
                    }
                });*/

            }
        });
    }

    private boolean verifyData() {
        if (book.getId() == null || book.getId().equalsIgnoreCase("")) {
            idET.setError("Please Enter Id");
            return false;
        }
        if (book.getTitle() == null || book.getTitle().equalsIgnoreCase("")) {
            titleET.setError("Please Enter Title");
            return false;
        }
        if (book.getAuthor() == null || book.getAuthor().equalsIgnoreCase("")) {
            authorET.setError("Please Enter Author");
            return false;
        }
        if (book.getPublisher() == null || book.getPublisher().equalsIgnoreCase("")) {
            publisherET.setError("Please Enter Books");
            return false;
        }
        if (book.getLanguage() == null || book.getLanguage().equalsIgnoreCase("")) {
            languageET.setError("Please Enter Language");
            return false;
        }
        if (book.getEdition() == null || book.getEdition().equalsIgnoreCase("")) {
            editionET.setError("Please Enter Edition");
            return false;
        }
        if (book.getRating() == null || book.getRating().equalsIgnoreCase("")) {
            ratingET.setError("Please Enter Rating");
            return false;
        }
        if (book.getYop() == null || book.getYop().equalsIgnoreCase("")) {
            yopET.setError("Please Enter Year Of Publish");
            return false;
        }
        if (book.getDescription() == null || book.getDescription().equalsIgnoreCase("")) {
            descriptionET.setError("Please Enter Description");
            return false;
        }
        if (book.getImage() == null || book.getImage().equalsIgnoreCase("") ) {
            linkImageET.setError("Please Enter Image URL");
            return false;
        }
        if(!Patterns.WEB_URL.matcher(book.getImage()).matches()){
            linkImageET.setError("Malformed URL to image");
            return false;
        }
        return true;
    }

    private void getEditTextData() {
        book = new Book();
        book.setId(idET.getText().toString());
        book.setTitle(titleET.getText().toString());
        book.setAuthor(authorET.getText().toString());
        book.setPublisher(publisherET.getText().toString());
        book.setLanguage(languageET.getText().toString());
        book.setEdition(editionET.getText().toString());
        book.setRating(ratingET.getText().toString());
        book.setYop(yopET.getText().toString());
        book.setDescription(descriptionET.getText().toString());
        book.setImage(linkImageET.getText().toString());
    }

    private void findAddOrModify() {
        if (mode.equalsIgnoreCase("m")) {
            addModifyBtn.setText("MODIFY");
            idET.setText(intent.getStringExtra("id"));
            idET.setKeyListener(null);
            titleET.setText(intent.getStringExtra("title"));
            authorET.setText(intent.getStringExtra("author"));
            publisherET.setText(intent.getStringExtra("publisher"));
            languageET.setText(intent.getStringExtra("language"));
            editionET.setText(intent.getStringExtra("edition"));
            ratingET.setText(intent.getStringExtra("rating"));
            yopET.setText(intent.getStringExtra("yop"));
            descriptionET.setText(intent.getStringExtra("description"));
            linkImageET.setText(intent.getStringExtra("linkToImage"));
        } else {
            addModifyBtn.setText("ADD");
        }
    }

    private void getIds() {
        idET = findViewById(R.id.etID);
        titleET = findViewById(R.id.etTitle);
        authorET = findViewById(R.id.etAuthor);
        publisherET = findViewById(R.id.etPublisher);
        languageET = findViewById(R.id.etLanguage);
        editionET = findViewById(R.id.etEdition);
        ratingET = findViewById(R.id.etRating);
        yopET = findViewById(R.id.etYOP);
        descriptionET = findViewById(R.id.etDescription);
        linkImageET = findViewById(R.id.etLinkToImage);
        addModifyBtn = findViewById(R.id.btnAddModify);
        linearLayout = findViewById(R.id.linearLayout);
    }
}
