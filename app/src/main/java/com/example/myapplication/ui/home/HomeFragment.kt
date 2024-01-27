package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.ItemAdapter
import com.example.myapplication.api.Movie
import com.example.myapplication.api.MovieResponse
import com.example.myapplication.api.TmdbApiService
import com.example.myapplication.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        // caso a lista de filmes ainda não tenha sido carregada, chame a API
        if (homeViewModel.popularList.value == null || homeViewModel.topRatedList.value == null || homeViewModel.nowPlayingList.value == null || homeViewModel.upcomingList.value == null) {
            println("chamando a API")
            loadDataFromApi()
        }
        // caso contrário, use os dados já carregados
        else {
            setupRecyclerViewPopulares(homeViewModel.popularList.value!!)
            setupRecyclerViewTopRated(homeViewModel.topRatedList.value!!)
            setupRecyclerViewNowPlaying(homeViewModel.nowPlayingList.value!!)
            setupRecyclerViewUpcoming(homeViewModel.upcomingList.value!!)
        }

        
        // Observa alterações nos dados e atualiza a interface do usuário quando necessário

        homeViewModel.popularList.observe(viewLifecycleOwner, Observer { movies ->
            setupRecyclerViewPopulares(movies)
        })

        homeViewModel.topRatedList.observe(viewLifecycleOwner, Observer { movies ->
            setupRecyclerViewTopRated(movies)
        })

        homeViewModel.nowPlayingList.observe(viewLifecycleOwner, Observer { movies ->
            setupRecyclerViewNowPlaying(movies)
        })

        homeViewModel.upcomingList.observe(viewLifecycleOwner, Observer { movies ->
            setupRecyclerViewUpcoming(movies)
        })


        return root
    }

    private fun loadDataFromApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val tmdbApiService = retrofit.create(TmdbApiService::class.java)

        // filmes populares
        val call = tmdbApiService.getPopularMovies(1,BuildConfig.API_KEY)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {

                    val movies = response.body()?.results
                    // Atualiza os dados no ViewModel
                    homeViewModel.setPopularMovies(movies!!)
                } else {
                    println("erro na chamada")
                    println(response.errorBody())
                    // mais detalhes do erro
                    println(response.code())
                }
            }

            // Lidar com falha na chamada
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) { }
        })

        // filmes mais bem avaliados
        val callTopRated = tmdbApiService.getTopRatedMovies(1,BuildConfig.API_KEY)
        callTopRated.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {

                    val movies = response.body()?.results
                    // Atualiza os dados no ViewModel
                    homeViewModel.setTopRatedMovies(movies!!)
                } else {
                    println("erro na chamada")
                    println(response.errorBody())
                    // mais detalhes do erro
                    println(response.code())
                }
            }

            // Lidar com falha na chamada
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) { }
        })

        // filmes em cartaz
        val callNowPlaying = tmdbApiService.getNowPlayingMovies(1,BuildConfig.API_KEY)
        callNowPlaying.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {

                    val movies = response.body()?.results
                    // Atualiza os dados no ViewModel
                    homeViewModel.setNowPlayingMovies(movies!!)
                } else {
                    println("erro na chamada")
                    println(response.errorBody())
                    // mais detalhes do erro
                    println(response.code())
                }
            }

            // Lidar com falha na chamada
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) { }
        })

        // filmes que serão lançados
        val callUpcoming = tmdbApiService.getUpcomingMovies(1,BuildConfig.API_KEY)
        callUpcoming.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {

                    val movies = response.body()?.results
                    // Atualiza os dados no ViewModel
                    homeViewModel.setUpcomingMovies(movies!!)
                } else {
                    println("erro na chamada")
                    println(response.errorBody())
                    // mais detalhes do erro
                    println(response.code())
                }
            }

            // Lidar com falha na chamada
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) { }
        })


    }

    private fun setupRecyclerViewPopulares(popularMovies: List<Movie>) {
        val recyclerPopular: RecyclerView = binding.recyclerPopular
        recyclerPopular.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        itemAdapter = ItemAdapter(popularMovies)
        recyclerPopular.adapter = itemAdapter
    }

    private fun setupRecyclerViewTopRated(movies: List<Movie>) {
        val recyclerTopRated: RecyclerView = binding.recyclerTopRated
        recyclerTopRated.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        itemAdapter = ItemAdapter(movies)
        recyclerTopRated.adapter = itemAdapter
    }

    private fun setupRecyclerViewNowPlaying(movies: List<Movie>) {
        val recyclerNowPlaying: RecyclerView = binding.recyclerNowPlaying
        recyclerNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        itemAdapter = ItemAdapter(movies)
        recyclerNowPlaying.adapter = itemAdapter
    }

    private fun setupRecyclerViewUpcoming(movies: List<Movie>) {
        val recyclerUpcoming: RecyclerView = binding.recyclerUpcoming
        recyclerUpcoming.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        itemAdapter = ItemAdapter(movies)
        recyclerUpcoming.adapter = itemAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}