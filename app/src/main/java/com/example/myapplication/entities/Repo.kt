package com.example.myapplication.entities

import android.content.Context
import androidx.room.Room
import com.example.myapplication.db.MovieDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repo(applicationContext: Context) {

    private val database = Room.databaseBuilder(
        applicationContext,
        MovieDB::class.java,
        "movies"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val filmeDao = database.movieDAO()

    suspend fun insertMovie(filme: Movie) {
        withContext(Dispatchers.IO) {
            filmeDao.insertMovie(filme)
        }
    }

    suspend fun obterFilme(id: Int): Movie {
        return withContext(Dispatchers.IO) {
            filmeDao.obterFilme(id)
        }
    }

    // obter favoritos
    suspend fun getFavorites(): List<Movie> {
        return withContext(Dispatchers.IO) {
            filmeDao.getFavorites()
        }
    }

    // exists
    suspend fun exists(id: Int): Int {
        return withContext(Dispatchers.IO) {
            filmeDao.exists(id)
        }
    }

    // isFavorite
    suspend fun isFavorite(id: Int): Int {
        return withContext(Dispatchers.IO) {
            filmeDao.isFavorite(id)
        }
    }

    // removeFavorite
    suspend fun removeFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            filmeDao.removeFavorite(id)
        }
    }

    // addFavorite
    suspend fun addFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            filmeDao.addFavorite(id)
        }
    }

    // removeWatched
    suspend fun removeWatched(id: Int) {
        withContext(Dispatchers.IO) {
            filmeDao.removeWatched(id)
        }
    }

    // addWatched
    suspend fun addWatched(id: Int) {
        withContext(Dispatchers.IO) {
            filmeDao.addWatched(id)
        }
    }

    // isWatched
    suspend fun isWatched(id: Int): Int {
        return withContext(Dispatchers.IO) {
            filmeDao.isWatched(id)
        }
    }

    // removeWatchList
    suspend fun removeWatchList(id: Int) {
        withContext(Dispatchers.IO) {
            filmeDao.removeWatchList(id)
        }
    }

    // addWatchList
    suspend fun addWatchList(id: Int) {
        withContext(Dispatchers.IO) {
            filmeDao.addWatchList(id)
        }
    }

    // isWatchList
    suspend fun isWatchList(id: Int): Int {
        return withContext(Dispatchers.IO) {
            filmeDao.isWatchList(id)
        }
    }

    // getWatched
    suspend fun getWatched(): List<Movie> {
        return withContext(Dispatchers.IO) {
            filmeDao.getWatched()
        }
    }

    // getWatchList
    suspend fun getWatchList(): List<Movie> {
        return withContext(Dispatchers.IO) {
            filmeDao.getWatchList()
        }
    }



}