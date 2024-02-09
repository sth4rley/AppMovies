package com.example.myapplication.ui.repository

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.ItemAdapter
import com.example.myapplication.MovieDetailsActivity
import com.example.myapplication.databinding.FragmentRepositoryBinding
import com.example.myapplication.entities.Movie
import kotlinx.coroutines.launch

class RepositoryFragment : Fragment() {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var repositoryViewModel: RepositoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        repositoryViewModel = ViewModelProvider(this).get(RepositoryViewModel::class.java)


        // Favoritos
        val recyclerFavorites: RecyclerView = binding.recyclerFavorites
        recyclerFavorites.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        repositoryViewModel.favoritesList.observe(viewLifecycleOwner, Observer {
            val favoritesAdapter = ItemAdapter(it)
            recyclerFavorites.adapter = favoritesAdapter
            favoritesAdapter.setOnItemClickListener(object : ItemAdapter.OnItemClickListener {
                override fun onItemClick(movie: Movie) {
                    openMovieDetails(movie)
                }
            })
        })

        // Assistir depois
        val recyclerWatchlist: RecyclerView = binding.recyclerWatchLater
        recyclerWatchlist.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        repositoryViewModel.watchLaterList.observe(viewLifecycleOwner, Observer {
            val watchLaterAdapter = ItemAdapter(it)
            recyclerWatchlist.adapter = watchLaterAdapter
            watchLaterAdapter.setOnItemClickListener(object : ItemAdapter.OnItemClickListener {
                override fun onItemClick(movie: Movie) {
                    openMovieDetails(movie)
                }
            })
        })

        // Assistidos
        val recyclerWatched: RecyclerView = binding.recyclerWatched
        recyclerWatched.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        repositoryViewModel.watchedList.observe(viewLifecycleOwner, Observer {
            val watchedAdapter = ItemAdapter(it)
            recyclerWatched.adapter = watchedAdapter
            watchedAdapter.setOnItemClickListener(object : ItemAdapter.OnItemClickListener {
                override fun onItemClick(movie: Movie) {
                    openMovieDetails(movie)
                }
            })
        })


        return root
    }


    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            repositoryViewModel.syncData(requireContext())
        }
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