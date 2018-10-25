package br.brilhante.gustavo.moviesearch.modules.moviedetails

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.brilhante.gustavo.feednews.api.ServerInteractor
import br.brilhante.gustavo.moviesearch.models.GenresItem
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.models.MovieInfo
import br.brilhante.gustavo.moviesearch.utils.DisposableManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<Movie>()

    var movieInfoLiveData = MutableLiveData<MovieInfo>()

    var genreTextLiveData = MutableLiveData<String>()

    fun getMovieFromIntent(intent: Intent) {
        intent.getParcelableExtra<Movie>("movie")?.let { movie: Movie ->
            movieLiveData.value = movie
            movie.id?.let {
                getMovieInfo(it.toString())
            }
        }
    }

    fun getMovieInfo(movieId: String) {
        DisposableManager.add(
            ServerInteractor().getMovieInfo(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    movieInfoLiveData.postValue(it)
                    processGenreText(it)
                }, {
                    movieInfoLiveData.value = null

                })
        )
    }

    fun processGenreText(movieInfo: MovieInfo) {
        var genreText = ""
        movieInfo.genres?.let { list: List<GenresItem?> ->
            for ((index, genre) in list.filterNotNull().withIndex()) {
                genreText += genre.name
                if (index < list.size - 2) genreText += ", "
                else if (index < list.size - 1) genreText += " and "
            }
        }
        genreTextLiveData.value = genreText
    }

}