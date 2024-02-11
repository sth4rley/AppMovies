package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import github.com.st235.lib_expandablebottombar.MenuItem
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // esconde a barra
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomBar = findViewById(R.id.nav_view)

        println("OnCreate MainActivity")

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_home, R.id.navigation_notifications
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        
        bottomBar.setupWithNavController(navController)

        val fab: FloatingActionButton = findViewById(R.id.search_fab)

        fab.setOnClickListener {
            searchMovieDialog()
        }

    }

    fun searchMovieDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_search, null)

        val editTextDialogo = dialogView.findViewById<EditText>(R.id.editTextMovieName)
        val buttonDialogo = dialogView.findViewById<Button>(R.id.btnSearch)

        builder.setView(dialogView)
        val dialog = builder.create()

        buttonDialogo.setOnClickListener {
            val movieName = editTextDialogo.text.toString()

            if (movieName.isEmpty()) {
                Toast.makeText(this, "Digite o nome do filme", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SearchResultsActivity::class.java)
                intent.putExtra("movie_name", movieName)
                startActivity(intent)
                dialog.dismiss()
            }

        }
        dialog.show()
    }
}
