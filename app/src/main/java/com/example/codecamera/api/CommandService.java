package com.example.codecamera.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CommandService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("executeCommand")
    Call<Void> executeCommand(@Header("apikey") String apikey, @Header("phyId") String phId, @Body CommandRequest commandRequest);
}



