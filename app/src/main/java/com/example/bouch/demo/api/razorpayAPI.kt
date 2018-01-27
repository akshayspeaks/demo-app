package com.example.bouch.demo.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by bouch on 27/1/18.
 */
interface razorpayAPI {
    companion object {
        fun create():razorpayAPI{
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("")
                    .build()

            return retrofit.create(razorpayAPI::class.java)
        }
    }
}