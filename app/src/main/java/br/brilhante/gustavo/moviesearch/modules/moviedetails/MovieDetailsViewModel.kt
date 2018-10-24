package br.brilhante.gustavo.moviesearch.modules.moviedetails

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import br.brilhante.gustavo.moviesearch.models.Movie

class MovieDetailsViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<Movie>()

    fun getMovieFromIntent(intent: Intent) {
        intent?.getParcelableExtra<Movie>("movie")?.let {
            movieLiveData.value = it
        }
    }

}