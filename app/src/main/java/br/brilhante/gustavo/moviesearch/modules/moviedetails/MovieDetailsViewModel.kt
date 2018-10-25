package br.brilhante.gustavo.moviesearch.modules.moviedetails

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.brilhante.gustavo.feednews.api.ServerInteractor
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.models.GenresItem
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.models.MovieInfo
import br.brilhante.gustavo.moviesearch.utils.DisposableManager
import br.brilhante.gustavo.moviesearch.utils.StringUtils
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
                }, {
                    movieInfoLiveData.value = null

                })
        )
    }

    fun processGenreText(context: Context, genres: List<GenresItem?>?) {
        var genreText = ""
        genres?.let { list: List<GenresItem?> ->
            var textList = list.map { it?.name }
            genreText = StringUtils.getWordsToPhrase(
                context.getString(R.string.and),
                textList
            )
        }
        genreTextLiveData.value = genreText
    }

}