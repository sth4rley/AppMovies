package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String
    ): Call<MovieResponse>
}
