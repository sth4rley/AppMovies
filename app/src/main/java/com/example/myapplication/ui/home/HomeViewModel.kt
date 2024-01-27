package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.api.Movie

class HomeViewModel : ViewModel() {
    private val _movieList: MutableLiveData<List<Movie>> = MutableLiveData()
    val movieList: LiveData<List<Movie>> get() = _movieList

    fun setMovies(movies: List<Movie>) {
        _movieList.value = movies
    }
}