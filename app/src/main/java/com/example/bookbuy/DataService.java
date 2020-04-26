package com.example.bookbuy;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DataService {

    @FormUrlEncoded
    @POST("signup")
    Call<String> createUser(@Body User user);

    @POST("login")
    Call<String> userlogin(@Body User user);

    @POST("books")
    Call<ArrayList<Book>> getBooks();

    @POST("delete")
    Call<String> deleteBook(@Body Book book);

    @POST("addUpdate")
    Call<String> addModifyBook(@Body Book book);

    @POST("getBook")
    Call<String> getBook(@Body Book book);
}
