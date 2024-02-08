package com.example.myapplication.ui

import com.example.myapplication.MovieDetailsActivity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.api.MovieResponse
import com.example.myapplication.api.TmdbApiService
import com.example.myapplication.entities.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val nomeFilme = intent.getStringExtra("movie_name")

        title = "Resultados para: $nomeFilme"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val tmdbApiService = retrofit.create(TmdbApiService::class.java)

        val call = tmdbApiService.searchMovies(nomeFilme!!, 1, BuildConfig.API_KEY)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {

                    var movies = response.body()?.results?.sortedByDescending { it.popularity }

                    val recyclerView = findViewById<RecyclerView>(R.id.recycler_search)

                    val columns = 3

                    recyclerView.layoutManager =  GridLayoutManager(this@SearchResultsActivity, columns)

                    val adapter = ItemAdapter(movies!!)
                    recyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object : ItemAdapter.OnItemClickListener {
                        override fun onItemClick(movie: Movie) {
                            openMovieDetails(movie)
                        }
                    })

                } else {
                    println("erro na chamada")
                    println(response.errorBody())
                    println(response.code())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) { }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun openMovieDetails(movie: Movie?) {
        val bundle = Bundle()
        bundle.putSerializable("movie", movie)
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}