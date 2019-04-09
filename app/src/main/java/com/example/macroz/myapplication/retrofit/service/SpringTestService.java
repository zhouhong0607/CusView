package com.example.macroz.myapplication.retrofit.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpringTestService {
    @GET("SpringTest_war_exploded/hi/say")
    Call<Void> getTest();
}
