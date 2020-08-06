package com.example.spinnerwithapi.api

import com.example.customnavigationdrawer.model.Shop
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
   private var apiInterface : ApiInterface

    companion object {
        const val Base_url ="https://myanmarmyinttar.sithuaungmm.com/api/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)

    }

    fun getShop() : Call<Shop> {
        return apiInterface.getShop()
    }
}
