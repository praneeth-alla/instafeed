package com.instafeed.data.api;

import com.instafeed.data.model.Data;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MediaNetworkService {

    @GET("Prod/users/self/media/recent/")
    Call<Data> getMedia(@Header("Authorization") String idToken);

    @POST("Prod/media/{media_id}/likes")
    Call<Void> postLike(@Path(value = "media_id") String value,
                        @Header("Authorization") String idToken);

    @DELETE("Prod/media/{media_id}/likes")
    Call<Void> postDisLike(@Path(value = "media_id") String value,
                           @Header("Authorization") String idToken);
}
