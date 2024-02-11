package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.BuildConfig
import com.example.myapplication.api.MovieResponse
import com.example.myapplication.api.TmdbApiService
import com.example.myapplication.entities.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {
    private val _popularList: MutableLiveData<List<Movie>> = MutableLiveData()
    val popularList: LiveData<List<Movie>> get() = _popularList

    private val _topRatedList: MutableLiveData<List<Movie>> = MutableLiveData()
    val topRatedList: LiveData<List<Movie>> get() = _topRatedList

    private val _nowPlayingList: MutableLiveData<List<Movie>> = MutableLiveData()
    val nowPlayingList: LiveData<List<Movie>> get() = _nowPlayingList

    private val _upcomingList: MutableLiveData<List<Movie>> = MutableLiveData()
    val upcomingList: LiveData<List<Movie>> get() = _upcomingList

    fun syncPopularMovies(tmdbApiService: TmdbApiService) {
        val call = tmdbApiService.getPopularMovies(1, BuildConfig.API_KEY)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    setPopularMovies(movies!!)
                } else {
                    println("ERRO NA CHAMADA: " + response.code())
                    println(response.errorBody())
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) { }
        })
    }

    fun syncTopRatedMovies(tmdbApiService: TmdbApiService) {
        val call = tmdbApiService.getTopRatedMovies(1, BuildConfig.API_KEY)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    setTopRatedMovies(movies!!)
                } else {
                    println("ERRO NA CHAMADA: " + response.code())
                    println(response.errorBody())
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) { }
        })
    }

    fun syncNowPlayingMovies(tmdbApiService: TmdbApiService) {
        val call = tmdbApiService.getNowPlayingMovies(1, BuildConfig.API_KEY)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    setNowPlayingMovies(movies!!)
                } else {
                    println("ERRO NA CHAMADA: " + response.code())
                    println(response.errorBody())
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) { }
        })
    }

    fun syncUpcomingMovies(tmdbApiService: TmdbApiService) {
        val call = tmdbApiService.getUpcomingMovies(1, BuildConfig.API_KEY)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    setUpcomingMovies(movies!!)
                } else {
                    println("ERRO NA CHAMADA: " + response.code())
                    println(response.errorBody())
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
        })
    }


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