package com.example.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.MovieDetailsActivity
import com.example.myapplication.api.TmdbApiService
import com.example.myapplication.ui.adapter.ItemAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.entities.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val tmdbApiService = retrofit.create(TmdbApiService::class.java)

        if(homeViewModel.popularList.value == null) {
            homeViewModel.syncPopularMovies(tmdbApiService)
        } else {
            setupRecycler(binding.recyclerPopular, homeViewModel.popularList.value!!)
        }

        if(homeViewModel.topRatedList.value == null) {
            homeViewModel.syncTopRatedMovies(tmdbApiService)
        } else {
            setupRecycler(binding.recyclerTopRated, homeViewModel.topRatedList.value!!)
        }

        if(homeViewModel.nowPlayingList.value == null) {
            homeViewModel.syncNowPlayingMovies(tmdbApiService)
        } else {
            setupRecycler(binding.recyclerNowPlaying, homeViewModel.nowPlayingList.value!!)
        }

        if(homeViewModel.upcomingList.value == null) {
            homeViewModel.syncUpcomingMovies(tmdbApiService)
        } else {
            setupRecycler(binding.recyclerUpcoming, homeViewModel.upcomingList.value!!)
        }

        homeViewModel.popularList.observe(viewLifecycleOwner, Observer { movies: List<Movie> ->
            setupRecycler(binding.recyclerPopular, movies)
        })

        homeViewModel.topRatedList.observe(viewLifecycleOwner, Observer { movies: List<Movie> ->
            setupRecycler(binding.recyclerTopRated, movies)
        })

        homeViewModel.nowPlayingList.observe(viewLifecycleOwner, Observer { movies: List<Movie> ->
            setupRecycler(binding.recyclerNowPlaying,movies)
        })

        homeViewModel.upcomingList.observe(viewLifecycleOwner, Observer { movies: List<Movie> ->
            setupRecycler(binding.recyclerUpcoming, movies)
        })

        return root
    }



    private fun setupRecycler (recycler: RecyclerView, movies: List<Movie>) {
        recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        itemAdapter = ItemAdapter(movies)
        recycler.adapter = itemAdapter
        itemAdapter.setOnItemClickListener(object : ItemAdapter.OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                openMovieDetails(movie)
            }
        })
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetailsActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}