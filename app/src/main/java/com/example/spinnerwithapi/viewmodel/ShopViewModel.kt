package com.example.spinnerwithapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customnavigationdrawer.model.Shop
import com.example.customnavigationdrawer.model.ShopDetail
import com.example.spinnerwithapi.api.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ShopViewModel : ViewModel() {
    var shop : MutableLiveData<Shop> = MutableLiveData()
    var loading : MutableLiveData<Boolean> = MutableLiveData()
    private val shopApi : Api = Api()
    var shop_name : MutableLiveData<String> = MutableLiveData()

    fun getShopData() : LiveData<Shop> = shop
    fun getLoading() : LiveData<Boolean> = loading
    fun getName() : LiveData<String> = shop_name

    fun setName(name:String){
        Log.d("shopName",name)
        shop_name.value = name

    }

    fun setShopData() {
        loading.value = true
        val apiCall = shopApi.getShop()
        apiCall.enqueue(object : Callback<Shop>{
            override fun onFailure(call: Call<Shop>, t: Throwable) {
                loading.value = true
            }

            override fun onResponse(call: Call<Shop>, response: Response<Shop>) {
                response.isSuccessful.let {
                    loading.value = false
                    shop.value = response.body()
                    Log.d("DetailResult>>>>>",response.body().toString())
                }
            }

        })
    }
}