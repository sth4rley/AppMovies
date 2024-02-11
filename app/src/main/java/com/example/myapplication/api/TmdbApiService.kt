package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TmdbApiService {
    // filmes populares

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String
    ): Call<MovieResponse>

    // filmes mais bem avaliados
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String
    ): Call<MovieResponse>

    // filmes em cartaz
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String
    ): Call<MovieResponse>

    // filmes que serão lançados
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String
    ): Call<MovieResponse>

    // pesquisa de filmes
    @GET("search/movie")
    fun searchMovies(
        @Query("language") language: String = "pt-BR",
        @Query("query") query: String,
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String
    ): Call<MovieResponse>

}
