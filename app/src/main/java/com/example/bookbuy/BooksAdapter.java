package com.example.bookbuy;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter
{

  class BooksViewHolder extends RecyclerView.ViewHolder
  {


      CardView userCv;
      TextView txtTitle;
      TextView txtAuthor;
      TextView txtPublisher;
      TextView txtEdition;
      ImageView imgbook;
      Button btnBuy;


      public BooksViewHolder(@NonNull View itemView)
      {
          super( itemView );

          userCv=(CardView)itemView.findViewById( R.id.usercv );
          txtTitle = (TextView)itemView.findViewById(R.id.txtbooktitle);
          txtAuthor = (TextView)itemView.findViewById(R.id.txtbookauthor);

          imgbook = (ImageView)itemView.findViewById(R.id.bookimage);

      }
  }


}
