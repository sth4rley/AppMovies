package com.example.myapplication.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.entities.Movie
import java.io.Serializable


data class MovieResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>
)

