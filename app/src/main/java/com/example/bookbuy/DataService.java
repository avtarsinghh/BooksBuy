package com.example.bookbuy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DataService {
    @POST("books")
    Call<ArrayList<Book>> getBooks();

    @POST("delete")
    Call<String> deleteBook(@Body Book book);

    @POST("addUpdate")
    Call<String> addModifyBook(@Body Book book);

    @POST("getBook")
    Call<String> getBook(@Body Book book);
}
