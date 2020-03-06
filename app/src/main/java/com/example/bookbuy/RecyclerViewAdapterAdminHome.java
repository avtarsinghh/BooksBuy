package com.example.bookbuy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterAdminHome extends RecyclerView.Adapter<RecyclerViewAdapterAdminHome.ViewHolder> {
    LayoutInflater layoutInflater;
    LinearLayout linearLayout;
    Context context;
    ArrayList<Books> books;

    public RecyclerViewAdapterAdminHome(Context context, ArrayList<Books> books, LinearLayout linearLayout) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.books = books;
        this.linearLayout = linearLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.admin_book_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titleTV.setText(books.get(position).getTitle());
        holder.authorTV.setText(books.get(position).getAuthor());
        holder.publisherTV.setText(books.get(position).getPublisher());
        holder.languageTV.setText(books.get(position).getLanguage());
        holder.editionTV.setText(books.get(position).getEdition());
        holder.ratingTV.setText(books.get(position).getRating());
        holder.yopTV.setText(books.get(position).getYop());
        Picasso.get().load(books.get(position).getImage()).fit().into(holder.imageBook);

        holder.modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminAddModifyBook.class);
                intent.putExtra("mode", "m");
                intent.putExtra("id", books.get(position).getId());
                intent.putExtra("linkToImage", books.get(position).getImage());
                intent.putExtra("title", books.get(position).getTitle());
                intent.putExtra("author", books.get(position).getAuthor());
                intent.putExtra("publisher", books.get(position).getPublisher());
                intent.putExtra("language", books.get(position).getLanguage());
                intent.putExtra("edition", books.get(position).getEdition());
                intent.putExtra("rating", books.get(position).getRating());
                intent.putExtra("yop", books.get(position).getYop());
                intent.putExtra("description", books.get(position).getDescription());
                context.startActivity(intent);
            }
        });


        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                new AlertDialog.Builder(context).setTitle("Delete Book").setMessage("Do you want to delete this book?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        linearLayout.setVisibility(View.VISIBLE);
                        db.collection("books").document(books.get(position).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(context, AdminHomePage.class);
                                        context.startActivity(intent);
                                        ((Activity)context).finish();
                                        linearLayout.setVisibility(View.GONE);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        linearLayout.setVisibility(View.GONE);
                                        Toast.makeText(context, "Failed to delete book!!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).setNegativeButton("No", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBook, imageDelete;
        TextView titleTV, authorTV, publisherTV, languageTV, editionTV, ratingTV, yopTV;
        Button modifyBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBook = itemView.findViewById(R.id.imageView);
            imageDelete = itemView.findViewById(R.id.deleteIV);
            titleTV = itemView.findViewById(R.id.title);
            authorTV = itemView.findViewById(R.id.author);
            publisherTV = itemView.findViewById(R.id.publisher);
            languageTV = itemView.findViewById(R.id.language);
            editionTV = itemView.findViewById(R.id.edition);
            ratingTV = itemView.findViewById(R.id.rating);
            yopTV = itemView.findViewById(R.id.yearOfPublish);
            modifyBtn = itemView.findViewById(R.id.modifyBtn);
        }
    }
}
