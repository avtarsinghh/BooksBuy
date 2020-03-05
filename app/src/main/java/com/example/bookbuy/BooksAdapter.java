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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Books> books;

    public BooksAdapter(Context context, ArrayList<Books> books) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.userhomepagecardview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        viewHolder.titleB.setText( books.get(position).getTitle());
        viewHolder.authorB.setText( books.get(position).getAuthor());
        viewHolder.publisherB.setText( books.get(position).getPublisher());
        viewHolder.editionB.setText( books.get(position).getEdition());
        Picasso.get().load( books.get(position).getImage()).fit().into(viewHolder.imgBook);


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView titleB, authorB, publisherB,  editionB;
        Button btnbuy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.bookimage);
            titleB = itemView.findViewById(R.id.txtbooktitle);
            authorB = itemView.findViewById(R.id.txtbookauthor);
            publisherB = itemView.findViewById(R.id.txtbookpublisher);
            editionB = itemView.findViewById(R.id.txtbookedition);
            btnbuy=itemView.findViewById(R.id.btnBuy);


            btnbuy.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText( v.getContext(),"Your book is booked,Buy from store.",Toast.LENGTH_SHORT ).show();
                    btnbuy.setText( "Booked" );
                }
            } );
        }
    }
}
