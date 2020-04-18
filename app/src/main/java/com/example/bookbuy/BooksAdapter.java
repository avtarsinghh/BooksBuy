package com.example.bookbuy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Book> books;

    public BooksAdapter(Context context, ArrayList<Book> books)
    {
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position)
    {
        viewHolder.titleB.setText( books.get(position).getTitle());
        viewHolder.authorB.setText( books.get(position).getAuthor());
        viewHolder.publisherB.setText( books.get(position).getPublisher());
        viewHolder.editionB.setText( books.get(position).getEdition());
        Picasso.get().load( books.get(position).getImage()).fit().into(viewHolder.imgBook);

        viewHolder.btnDescription.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, UserBookBuy.class);
                intent.putExtra( "id",books.get( position ).getId() );
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
        } );

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imgBook;
        TextView titleB, authorB, publisherB,  editionB;
        Button btnDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById( R.id.usercv );
            imgBook = itemView.findViewById(R.id.bookimage);
            titleB = itemView.findViewById(R.id.txtbooktitle);
            authorB = itemView.findViewById(R.id.txtbookauthor);
            publisherB = itemView.findViewById(R.id.txtbookpublisher);
            editionB = itemView.findViewById(R.id.txtbookedition);
            btnDescription =itemView.findViewById(R.id.btnDescription);

        }
    }
}
