package com.daffodil.renters.api

import com.daffodil.renters.application.RentersApplication
import com.daffodil.renters.model.Listing
import com.daffodil.renters.model.ListingSkeletal
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitClient : RentersApplication() {

    private var retrofit: Retrofit

    //Constructor
    init {
        val apiURL = "http://$serverIpAddress:$serverPortNumber"
        retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    companion object {
        private var mInstance: RetrofitClient? = null

        @Synchronized
        fun getInstance(): RetrofitClient {
            if (mInstance == null) {
                mInstance = RetrofitClient()
            }
            return mInstance!!
        }
    }

    fun getAPIClient(): APIClient {
        return retrofit.create(APIClient::class.java)
    }

    interface APIClient {

        @GET("daffodil/api/listing")
        fun getMinListings(@Query("id") id: Long? = null, @Query("min") minListing: Boolean = true): Call<List<ListingSkeletal>>


        @GET("daffodil/api/listing")
        fun getListings(@Query("id") id: Long? = null, @Query("min") minListing: Boolean = false): Call<List<Listing>>

    }

}