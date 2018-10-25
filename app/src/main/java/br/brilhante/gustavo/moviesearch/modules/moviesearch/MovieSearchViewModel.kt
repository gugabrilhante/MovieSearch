package br.brilhante.gustavo.moviesearch.modules.moviesearch

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.brilhante.gustavo.feednews.api.ServerInteractor
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.models.MovieList
import br.brilhante.gustavo.moviesearch.modules.moviedetails.MovieDetailsActivity
import br.brilhante.gustavo.moviesearch.utils.DisposableManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieSearchViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<List<Movie>>()

    fun getUpcomingMovieList() {
        movieLiveData.value = emptyList()
        DisposableManager.add(
            ServerInteractor().getUpcomingMovies(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response: MovieList ->

                    response.results?.let {
                        movieLiveData.postValue(it.filterNotNull())
                    }

                }, {
                    movieLiveData.value = emptyList()
                })
        )
    }

    fun searchMovieList(name:String) {
        movieLiveData.value = emptyList()
        DisposableManager.add(
            ServerInteractor().searchMovie(name, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response: MovieList ->

                    response.results?.let {
                        movieLiveData.postValue(it.filterNotNull())
                    }

                }, {
                    movieLiveData.value = emptyList()
                })
        )
    }

    fun goToMovieDetails(context: Context, movie: Movie) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("movie", movie)
        context.startActivity(intent)
    }

    fun getNextMoviePageList() {
        //TODO keep last page request value, increment and return a new list
    }

}