package com.example.bookbuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterAdminHome extends RecyclerView.Adapter<RecyclerViewAdapterAdminHome.ViewHolder> {
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Books> books;

    public RecyclerViewAdapterAdminHome(Context context, ArrayList<Books> books) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.admin_book_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTV.setText(books.get(position).getTitle());
        holder.authorTV.setText(books.get(position).getAuthor());
        holder.publisherTV.setText(books.get(position).getPublisher());
        holder.languageTV.setText(books.get(position).getLanguage());
        holder.editionTV.setText(books.get(position).getEdition());
        holder.ratingTV.setText(books.get(position).getRating());
        holder.yopTV.setText(books.get(position).getYop());
        Picasso.get().load(books.get(position).getImage()).into(holder.imageBook);
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
        }
    }
}
