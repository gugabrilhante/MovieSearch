package br.brilhante.gustavo.moviesearch.modules.moviedetails

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.brilhante.gustavo.moviesearch.models.Movie

class MovieDetailsViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<Movie>()

    fun getMovieFromIntent(intent: Intent) {
        intent.getParcelableExtra<Movie>("movie")?.let {
            movieLiveData.value = it
        }
    }

}