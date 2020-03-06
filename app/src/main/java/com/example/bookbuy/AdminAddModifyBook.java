package com.example.bookbuy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.regex.Matcher;

public class AdminAddModifyBook extends AppCompatActivity {
    Intent intent;
    String mode;
    Button addModifyBtn;
    Books books;
    EditText idET, titleET, authorET, publisherET, languageET, editionET, ratingET, yopET, descriptionET, linkImageET;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_modify_book);
        getIds();

        firestore = FirebaseFirestore.getInstance();

        intent = getIntent();
        mode = intent.getStringExtra("mode");
        findAddOrModify();
        addModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditTextData();
                firestore.collection("books").document(books.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists() && mode.equalsIgnoreCase("a")){
                            Toast.makeText(AdminAddModifyBook.this, "This Id for book already exists", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if (verifyData()) {
                                firestore.collection("books").document(books.getId()).set(books).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(getApplicationContext(), AdminHomePage.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }
                                });
                            }
                        }
                    }
                });

            }
        });
    }

    private boolean verifyData() {
        if (books.getId() == null || books.getId().equalsIgnoreCase("")) {
            idET.setError("Please Enter Id");
            return false;
        }
        if (books.getTitle() == null || books.getTitle().equalsIgnoreCase("")) {
            titleET.setError("Please Enter Title");
            return false;
        }
        if (books.getAuthor() == null || books.getAuthor().equalsIgnoreCase("")) {
            authorET.setError("Please Enter Author");
            return false;
        }
        if (books.getPublisher() == null || books.getPublisher().equalsIgnoreCase("")) {
            publisherET.setError("Please Enter Books");
            return false;
        }
        if (books.getLanguage() == null || books.getLanguage().equalsIgnoreCase("")) {
            languageET.setError("Please Enter Language");
            return false;
        }
        if (books.getEdition() == null || books.getEdition().equalsIgnoreCase("")) {
            editionET.setError("Please Enter Edition");
            return false;
        }
        if (books.getRating() == null || books.getRating().equalsIgnoreCase("")) {
            ratingET.setError("Please Enter Rating");
            return false;
        }
        if (books.getYop() == null || books.getYop().equalsIgnoreCase("")) {
            yopET.setError("Please Enter Year Of Publish");
            return false;
        }
        if (books.getDescription() == null || books.getDescription().equalsIgnoreCase("")) {
            descriptionET.setError("Please Enter Description");
            return false;
        }
        if (books.getImage() == null || books.getImage().equalsIgnoreCase("") ) {
            linkImageET.setError("Please Enter Image URL");
            return false;
        }
        if(!Patterns.WEB_URL.matcher(books.getImage()).matches()){
            linkImageET.setError("Malformed URL to image");
            return false;
        }
        return true;
    }

    private void getEditTextData() {
        books = new Books();
        books.setId(idET.getText().toString());
        books.setTitle(titleET.getText().toString());
        books.setAuthor(authorET.getText().toString());
        books.setPublisher(publisherET.getText().toString());
        books.setLanguage(languageET.getText().toString());
        books.setEdition(editionET.getText().toString());
        books.setRating(ratingET.getText().toString());
        books.setYop(yopET.getText().toString());
        books.setDescription(descriptionET.getText().toString());
        books.setImage(linkImageET.getText().toString());
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
    }
}
