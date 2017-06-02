package com.dentaltw.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Scott on 2017/6/2.
 */

public interface Webservice {
    @GET("/users/{userId}")
    Call<User> getUser(@Path("userId") String userId);
}
