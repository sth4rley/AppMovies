package com.example.myapplication.ui.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.entities.Movie
import com.example.myapplication.entities.Repo

class RepositoryViewModel  : ViewModel() {
    private val _favoritesList: MutableLiveData<List<Movie>> = MutableLiveData()
    val favoritesList: LiveData<List<Movie>> get() = _favoritesList

    private val _watchLaterList: MutableLiveData<List<Movie>> = MutableLiveData()
    val watchLaterList: LiveData<List<Movie>> get() = _watchLaterList

    private val _watchedList: MutableLiveData<List<Movie>> = MutableLiveData()
    val watchedList: LiveData<List<Movie>> get() = _watchedList

    fun setFavoritesMovies(movies: List<Movie>) {
        _favoritesList.value = movies
    }

    fun setWatchLaterMovies(movies: List<Movie>) {
        _watchLaterList.value = movies
    }

    fun setWatchedMovies(movies: List<Movie>) {
        _watchedList.value = movies
    }

    fun getFavorites(): List<Movie> {
        return _favoritesList.value!!
    }

    fun getWatchLater(): List<Movie> {
        return _watchLaterList.value!!
    }

    fun getWatched(): List<Movie> {
        return _watchedList.value!!
    }

    suspend fun syncData(context: Context) {
        // chama a funcao repository.getAll() e atualiza o valor de _favoritesList, _watchLaterList e _watchedList
        val repository = Repo(context)
        setFavoritesMovies(repository.getFavorites())
        setWatchLaterMovies(repository.getWatchList())
        setWatchedMovies(repository.getWatched())
    }

}