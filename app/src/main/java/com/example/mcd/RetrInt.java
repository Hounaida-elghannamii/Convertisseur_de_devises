package com.example.mcd;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrInt {
    @GET("/hounaida/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
