package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.entities.Movie
import com.example.myapplication.entities.Repo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val movie = intent.getSerializableExtra("movie") as Movie

        setupData(movie)

        // Add favorite button
        val fabAdd = findViewById<FloatingActionButton>(R.id.btnAddFavorite)


        fabAdd.setOnClickListener {
            lifecycleScope.launch {
                toggleFavoriteStatus(movie)
            }
        }

        // Add watchlist button
        val fabAddWatchlist = findViewById<FloatingActionButton>(R.id.btnAddWatchList)


        fabAddWatchlist.setOnClickListener {
            lifecycleScope.launch {
                toggleWatchListStatus(movie)
            }
        }

        // Add watched button
        val fabAddWatched = findViewById<FloatingActionButton>(R.id.btnAddWatched)


        fabAddWatched.setOnClickListener {
            lifecycleScope.launch {
                toggleWatchedStatus(movie)
            }
        }



    }

    private fun setupData(movie: Movie) {
        val imageView = findViewById<ImageView>(R.id.movieImageView)
        val titleView = findViewById<TextView>(R.id.titleTextView)

        title = movie.title
        titleView.text = movie.title

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .into(imageView)
    }

    private suspend fun toggleFavoriteStatus(movie: Movie) {
        val repository = Repo(application)

        if (repository.exists(movie.id) == 0) {
            movie.favorite = true
            repository.insertMovie(movie)
        } else {
            if (repository.isFavorite(movie.id) == 1) {
                repository.removeFavorite(movie.id)
            } else {
                repository.addFavorite(movie.id)
            }
        }
    }

    private suspend fun toggleWatchedStatus(movie: Movie) {
        val repository = Repo(application)

        if (repository.exists(movie.id) == 0) {
            movie.watched = true
            repository.insertMovie(movie)
        } else {
            if (repository.isWatched(movie.id) == 1) {
                repository.removeWatched(movie.id)
            } else {
                repository.addWatched(movie.id)
            }
        }
    }

    private suspend fun toggleWatchListStatus(movie: Movie) {
        val repository = Repo(application)

        if (repository.exists(movie.id) == 0) {
            movie.watchlist = true
            repository.insertMovie(movie)
        } else {
            if (repository.isWatchList(movie.id) == 1) {
                repository.removeWatchList(movie.id)
            } else {
                repository.addWatchList(movie.id)
            }
        }
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
}
