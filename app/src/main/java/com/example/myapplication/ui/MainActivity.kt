package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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