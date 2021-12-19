package com.example.foodblogs.retrofit

import com.example.foodblogs.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("guljar-rivi/server/db")
    suspend fun getFoodDetails(): Response<BaseResponse>
}