package com.example.codecamera.db;

import com.example.codecamera.api.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DatabaseService {

    @Headers({
            "apikey: b2e1a685386a44ca9f68-2c05057103f",
            "tkn: BHUVUDhdwpqncxi223c63caaclEEASaq"
    })
    @GET("getUserList")
    Call<List<UserData>> getUserList();
}