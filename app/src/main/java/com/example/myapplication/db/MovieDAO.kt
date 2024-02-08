package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.entities.Movie

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun obterFilme(id: Int): Movie

    @Insert
    suspend fun insertMovie(filme: Movie)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovie(id: Int)

    @Query("UPDATE movies SET favorite = :favorite, watchlist = :watchlist, watched = :watched WHERE id = :id")
    suspend fun updateMovie(id: Int, favorite: Boolean, watchlist: Boolean, watched: Boolean)


    @Query("SELECT * FROM movies WHERE favorite = 1")
    suspend fun getFavorites(): List<Movie>

    @Query("SELECT * FROM movies WHERE watched = 1")
    suspend fun getWatched(): List<Movie>

    @Query("SELECT * FROM movies WHERE watchlist = 1")
    suspend fun getWatchList(): List<Movie>



    // verifica se o filme existe na base de dados
    @Query("SELECT COUNT(*) FROM movies WHERE id = :id")
    suspend fun exists(id: Int): Int


    // define a coluna de favoritos como 1
    @Query("UPDATE movies SET favorite = 1 WHERE id = :id")
    suspend fun addFavorite(id: Int)

    // define a coluna de favoritos como 0
    @Query("UPDATE movies SET favorite = 0 WHERE id = :id")
    suspend fun removeFavorite(id: Int)

    // verifica se o filme é favorito e retorna true ou false
    @Query("SELECT COUNT(*) FROM movies WHERE id = :id AND favorite = 1")
    suspend fun isFavorite(id: Int): Int


    // define a coluna de assistidos como 1
    @Query("UPDATE movies SET watched = 1 WHERE id = :id")
    suspend fun addWatched(id: Int)

    // define a coluna de assistidos como 0
    @Query("UPDATE movies SET watched = 0 WHERE id = :id")
    suspend fun removeWatched(id: Int)

    // verifica se o filme é assistido e retorna true ou false
    @Query("SELECT COUNT(*) FROM movies WHERE id = :id AND watched = 1")
    suspend fun isWatched(id: Int): Int


    // define a coluna de watchlist como 1
    @Query("UPDATE movies SET watchlist = 1 WHERE id = :id")
    suspend fun addWatchList(id: Int)

    // define a coluna de watchlist como 0
    @Query("UPDATE movies SET watchlist = 0 WHERE id = :id")
    suspend fun removeWatchList(id: Int)

    // verifica se o filme está na watchlist e retorna true ou false
    @Query("SELECT COUNT(*) FROM movies WHERE id = :id AND watchlist = 1")
    suspend fun isWatchList(id: Int): Int


    // elimina os filmes que nao estao em nenhuma lista
    @Query("DELETE FROM movies WHERE favorite = 0 AND watched = 0 AND watchlist = 0")
    suspend fun deleteNotListedMovies()



}