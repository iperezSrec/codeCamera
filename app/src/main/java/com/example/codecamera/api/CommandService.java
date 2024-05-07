package com.example.codecamera.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CommandService {

    @Headers({
            "apikey: b2e1a685386a44ca9f68-2c05057103f",
            "Content-Type: application/json"
    })
    @POST("executeCommand")
    Call<Void> executeCommand(@Header("phyId") String phId, @Body CommandRequest commandRequest);
}



