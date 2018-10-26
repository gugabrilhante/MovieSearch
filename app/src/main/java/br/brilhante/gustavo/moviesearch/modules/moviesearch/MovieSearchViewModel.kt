package br.brilhante.gustavo.moviesearch.modules.moviesearch

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.brilhante.gustavo.feednews.api.ServerInteractor
import br.brilhante.gustavo.moviesearch.database.AppDatabase
import br.brilhante.gustavo.moviesearch.database.MoviesDao
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.models.MovieList
import br.brilhante.gustavo.moviesearch.modules.moviedetails.MovieDetailsActivity
import br.brilhante.gustavo.moviesearch.modules.moviesearch.enums.MovieSearchType
import br.brilhante.gustavo.moviesearch.utils.DisposableManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieSearchViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<Pair<List<Movie>, Boolean>>()
    var hasMovieListSavedLiveData = MutableLiveData<Boolean>()
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var showErrorMessageLiveDatabase = MutableLiveData<String>()

    private var lastSearchType = MovieSearchType.UPCOMING

    fun checkForSavedMovieList() {
        val movieDao = AppDatabase.INSTANCE?.MoviesDao()
        movieDao?.let { dao: MoviesDao ->
            DisposableManager.add(
                dao.getMovieList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        hasMovieListSavedLiveData.postValue(it.isNotEmpty())
                        movieLiveData.postValue(Pair(it, false))
                    }, {
                        hasMovieListSavedLiveData.value = false
                    })
            )
        } ?: run {
            hasMovieListSavedLiveData.value = false
        }
    }

    fun getUpcomingMovieList() {
        isLoadingLiveData.value = true
        lastSearchType = MovieSearchType.UPCOMING
        DisposableManager.add(
            ServerInteractor().getUpcomingMovies(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response: MovieList ->
                    response.results?.let {
                        movieLiveData.postValue(Pair(it.filterNotNull(), true))
                        isLoadingLiveData.value = false
                    }

                }, {
                    movieLiveData.value = Pair(emptyList(), false)
                    isLoadingLiveData.value = false
                    showErrorMessageLiveDatabase.value = it.message
                })
        )
    }

    fun searchMovieList(name: String) {
        isLoadingLiveData.value = true
        lastSearchType = MovieSearchType.SEARCH
        DisposableManager.add(
            ServerInteractor().searchMovie(name, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response: MovieList ->

                    response.results?.let {
                        movieLiveData.postValue(Pair(it.filterNotNull(), true))
                        isLoadingLiveData.value = false
                    }

                }, {
                    movieLiveData.value = Pair(emptyList(), false)
                    isLoadingLiveData.value = false
                    showErrorMessageLiveDatabase.value = it.message
                })
        )
    }

    fun updateMovieList(name: String?) {
        when (lastSearchType) {
            MovieSearchType.SEARCH -> name?.let {
                if (it.isBlank() || it.isEmpty()) getUpcomingMovieList() else searchMovieList(name)
            }
            MovieSearchType.UPCOMING -> getUpcomingMovieList()
        }
    }

    fun insertListOnDataBase(context: Context, movieList: List<Movie>) {
        val movieDao = AppDatabase.INSTANCE?.MoviesDao()
        movieDao?.let {
            DisposableManager.add(
                Completable.fromAction { movieDao.insertMovieList(movieList) }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            )
        }
    }

    fun goToMovieDetails(context: Context, activityOptionsCompat: ActivityOptionsCompat, movie: Movie) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("movie", movie)
        context.startActivity(intent, activityOptionsCompat.toBundle())
    }

}