package com.example.myapplication.ui.repository

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.ItemAdapter
import com.example.myapplication.MovieDetailsActivity
import com.example.myapplication.databinding.FragmentNotificationsBinding
import com.example.myapplication.entities.Movie
import com.example.myapplication.entities.Repo
import kotlinx.coroutines.launch

class RepositoryFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recycler_favorites = binding.recyclerFavorites
        recycler_favorites.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val recycler_watchlist = binding.recyclerWatchLater
        recycler_watchlist.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val recycler_watched = binding.recyclerWatched
        recycler_watched.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)


        return root
    }


    override fun onResume() {
        super.onResume()
        val repo = Repo(requireContext())
        lifecycleScope.launch {
            setupRecycler(binding.recyclerFavorites,repo.getFavorites())
            setupRecycler(binding.recyclerWatchLater,repo.getWatchList())
            setupRecycler(binding.recyclerWatched,repo.getWatched())
        }
    }

    fun openMovieDetails(movie: Movie?) {
        val bundle = Bundle()
        bundle.putSerializable("movie", movie)
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}