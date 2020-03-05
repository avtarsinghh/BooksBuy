package com.example.bookbuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    LayoutInflater layoutInflateruh;
    Context contextuh;
    ArrayList<Books> booksuh;

    public BooksAdapter(Context contextuh, ArrayList<Books> books) {
        layoutInflateruh = LayoutInflater.from(contextuh);
        this.contextuh = contextuh;
        this.booksuh = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflateruh.inflate(R.layout.userhomepagecardview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        viewHolder.titleB.setText(booksuh.get(position).getTitle());
        viewHolder.authorB.setText(booksuh.get(position).getAuthor());
        viewHolder.publisherB.setText(booksuh.get(position).getPublisher());
        viewHolder.editionB.setText(booksuh.get(position).getEdition());
        Picasso.get().load(booksuh.get(position).getImage()).into(viewHolder.imgBook);

    }

    @Override
    public int getItemCount() {
        return booksuh.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        CardView cardViewUH;
        TextView titleB, authorB, publisherB,  editionB;
        Button btnbuy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewUH=itemView.findViewById(R.id.usercv);
            imgBook = itemView.findViewById(R.id.bookimage);
            titleB = itemView.findViewById(R.id.txtbooktitle);
            authorB = itemView.findViewById(R.id.txtbookauthor);
            publisherB = itemView.findViewById(R.id.txtbookpublisher);
            editionB = itemView.findViewById(R.id.txtbookedition);
            btnbuy=itemView.findViewById(R.id.btnBuy);
        }
    }
}
