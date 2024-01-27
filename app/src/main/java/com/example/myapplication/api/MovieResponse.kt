package com.example.myapplication.api


data class MovieResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int,
    val adult: Boolean,
    val video: Boolean,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>
)