package com.example.bookbuy;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:3012/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
