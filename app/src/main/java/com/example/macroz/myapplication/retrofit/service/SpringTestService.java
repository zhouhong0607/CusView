package com.example.macroz.myapplication.retrofit.service;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SpringTestService {
    @GET("SpringTest_war_exploded/hi/say")
    Observable<Void> getTest();
}
