package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies", indices = [Index(value = ["favorite"]), Index(value = ["watchlist"]), Index(value = ["watched"])])
data class Movie(
    @PrimaryKey(autoGenerate = true)
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
    var favorite: Boolean,
    var watchlist: Boolean,
    var watched: Boolean,
    //var genre_ids: List<Int>
) : Serializable