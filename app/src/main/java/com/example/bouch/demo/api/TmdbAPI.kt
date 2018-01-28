package com.example.bouch.demo.api

import com.example.bouch.demo.model.SearchResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by bouch on 27/1/18.
 */
interface TmdbAPI {

    @GET("search/movie")
    fun searchMovies(@Query("api_key") api_key:String,
                     @Query("query") query:String):Observable<SearchResult>

    /*companion object {
        fun create(): TmdbAPI {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.themoviedb.org/3")
                    .build()

            return retrofit.create(TmdbAPI::class.java)
        }
    }*/ //for interface scope
}