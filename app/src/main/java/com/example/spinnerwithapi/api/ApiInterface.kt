package com.example.spinnerwithapi.api

import com.example.customnavigationdrawer.model.Shop
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("shop")
    fun getShop(): Call<Shop>
}