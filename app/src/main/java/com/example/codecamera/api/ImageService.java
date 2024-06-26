package com.example.codecamera.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ImageService {

    @Headers({
            "fsp-token: EkM7EsJmYo6BJPfC",
            "fsp-path: apikeys/43f42a657916ee7d035c4ee200c396a5e2396a661d2bec8053df06b52b8e499c/Library/temp/",
            "permanent-access: true",
            "Content-Type: image/png"
    })
    @PUT("object/{name}.png")
    Call<Void> uploadImage(@Path("name") String name, @Body RequestBody imageRequest);
}



