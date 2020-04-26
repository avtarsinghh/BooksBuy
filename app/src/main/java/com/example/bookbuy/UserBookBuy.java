package com.example.bookbuy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class UserBookBuy extends AppCompatActivity {
    TextView edTitle, edAuthor, edPublisher, edLanguage, edEdition, edRating, edYOP, eddescription;
    ImageView imgBookBB;
    Button btnPurchase;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_buy);

        intent = getIntent();

        edTitle = (TextView) findViewById(R.id.edtTitleBB);
        edAuthor = (TextView) findViewById(R.id.edtAuthorBB);
        edPublisher = (TextView) findViewById(R.id.edtPublisherBB);
        edLanguage = (TextView) findViewById(R.id.edtLanguageBB);
        edEdition = (TextView) findViewById(R.id.edtEditionBB);
        edRating = (TextView) findViewById(R.id.edtRatingBB);
        edYOP = (TextView) findViewById(R.id.edtYOPbb);
        eddescription = (TextView) findViewById(R.id.edtDescriptionBB);
        imgBookBB = (ImageView) findViewById(R.id.imgImageBB);

        btnPurchase = (Button) findViewById(R.id.btnClick);

        String strTitle = getIntent().getStringExtra("title");
        String strAuthor = getIntent().getStringExtra("author");
        String strPublisher = getIntent().getStringExtra("publisher");
        String strLang = getIntent().getStringExtra("language");
        String strEdition = getIntent().getStringExtra("edition");
        String strRating = getIntent().getStringExtra("rating");
        String strYOP = getIntent().getStringExtra("yop");
        String strDescription = getIntent().getStringExtra("description");
        String strImage = getIntent().getStringExtra("linkToImage");


        edTitle.setText(strTitle);
        edAuthor.setText(strAuthor);
        edPublisher.setText(strPublisher);
        edLanguage.setText(strLang);
        edEdition.setText(strEdition);
        edRating.setText(strRating);
        edYOP.setText(strYOP);
        eddescription.setText(strDescription);

        Picasso.get().load(strImage).into(imgBookBB);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserBookBuy.this);
                alertDialogBuilder.setTitle("Information")
                        .setMessage("Book is resreved. You can buy it from shop")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(UserBookBuy.this, UserHomePage.class);
                                startActivity(intent);
                            }
                        });
                alertDialogBuilder.show();
            }
        });

    }
}



