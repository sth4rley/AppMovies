package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.entities.Movie

class HomeViewModel : ViewModel() {
    private val _popularList: MutableLiveData<List<Movie>> = MutableLiveData()
    val popularList: LiveData<List<Movie>> get() = _popularList

    private val _topRatedList: MutableLiveData<List<Movie>> = MutableLiveData()
    val topRatedList: LiveData<List<Movie>> get() = _topRatedList

    private val _nowPlayingList: MutableLiveData<List<Movie>> = MutableLiveData()
    val nowPlayingList: LiveData<List<Movie>> get() = _nowPlayingList

    private val _upcomingList: MutableLiveData<List<Movie>> = MutableLiveData()
    val upcomingList: LiveData<List<Movie>> get() = _upcomingList

    fun setPopularMovies(movies: List<Movie>) {
        _popularList.value = movies
    }

    fun setTopRatedMovies(movies: List<Movie>) {
        _topRatedList.value = movies
    }

    fun setNowPlayingMovies(movies: List<Movie>) {
        _nowPlayingList.value = movies
    }

    fun setUpcomingMovies(movies: List<Movie>) {
        _upcomingList.value = movies
    }

}