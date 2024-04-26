package com.example.codecamera.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiUser {

    @POST("oasis/executeCommand")
    Call<Void> fichar(
            @Header("apikey") String apiKey,
            @Header("phyid") String phyId,
            @Body FicharRequest request
    );
}