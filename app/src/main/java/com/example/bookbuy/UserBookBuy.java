package com.example.bookbuy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookBuy extends AppCompatActivity {
    EditText edId, edTitle, edAuthor, edPublisher, edLanguage, edEdition, edRating, edYOP, eddescription;
    Button btnPurchase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_buy);
        edId = (EditText)findViewById(R.id.edt1);
        edTitle = (EditText)findViewById(R.id.edt2);
        edAuthor = (EditText)findViewById(R.id.edt3);
        edPublisher = (EditText)findViewById(R.id.edt4);
        edLanguage = (EditText)findViewById(R.id.edt5);
        edEdition = (EditText)findViewById(R.id.edt6);
        edRating = (EditText)findViewById(R.id.edt7);
        edYOP = (EditText)findViewById(R.id.edt8);
        eddescription = (EditText)findViewById(R.id.edt9);
        btnPurchase = (Button)findViewById(R.id.btnClick);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}



