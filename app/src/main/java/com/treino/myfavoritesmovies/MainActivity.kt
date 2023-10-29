package com.treino.myfavoritesmovies

import android.annotation.SuppressLint
import android.media.session.MediaSession.Token
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.treino.myfavoritesmovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initViews
        binding.apply {
            btnSearch.setOnClickListener {
                //validate Input
                if (etSearch.text.toString().isEmpty()) {
                    Toast.makeText(this@MainActivity, "please fill this input", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    //request to server
                    cardMovie.visibility = View.INVISIBLE
                    val queue = Volley.newRequestQueue(this@MainActivity)
                    val url =
                        "https://omdbapi.com/?apikey=" + Token + "&t=" + etSearch.text.toString()

                    val jsonObjectRequest = JsonObjectRequest(url, { response ->
                        cardMovie.visibility = View.VISIBLE
                        val title = response.getString("Title")
                        val Plot = response.getString("Plot")
                        val Poster = response.getString("Poster")
                        //set data
                        Glide.with(this@MainActivity)
                            .load(Poster)
                            .intom(movieCover)

                        movieTitle.text = title
                        movieDescription.text = Plot.toString()

                    }, { error ->
                        cardMovie.visibility = View.INVISIBLE
                        Toast.makeText(
                            this@MainActivity,
                            "error is :" + error.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                    queue.add(jsonObjectRequest)

                    showMore.setOnClickListener{

                    }
                }

            }
        }

    }
}