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
        if (homeViewModel.movieList.value == null) {
            println("chamando a API")
            loadDataFromApi()
        }
        // caso contrário, use os dados já carregados
        else {
            setupRecyclerView(homeViewModel.movieList.value!!)
        }

        // Observa alterações nos dados e atualiza a interface do usuário quando necessário
        homeViewModel.movieList.observe(viewLifecycleOwner, Observer { movies ->
            setupRecyclerView(movies)
        })

        return root
    }

    private fun loadDataFromApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val tmdbApiService = retrofit.create(TmdbApiService::class.java)

        val call = tmdbApiService.getPopularMovies(1,BuildConfig.API_KEY)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {

                    val movies = response.body()?.results
                    // Atualiza os dados no ViewModel
                    homeViewModel.setMovies(movies!!)
                } else {
                    println("erro na chamada")
                    println(response.errorBody())
                    // mais detalhes do erro
                    println(response.code())
                }
            }


            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Lidar com falha na chamada
            }
        })
    }

    private fun setupRecyclerView(movies: List<Movie>) {
        val recyclerView: RecyclerView = binding.meuRecycler
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        itemAdapter = ItemAdapter(movies)
        recyclerView.adapter = itemAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}