package br.brilhante.gustavo.moviesearch.modules.moviesearch

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import br.brilhante.gustavo.feednews.api.ServerInteractor
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.models.UpcomingResponse
import br.brilhante.gustavo.moviesearch.utils.DisposableManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieSearchViewModel(app: Application) : AndroidViewModel(app) {

    var movieLiveData = MutableLiveData<List<Movie>>()

    fun getMovieList() {
        movieLiveData.value = emptyList()
        DisposableManager.add(
            ServerInteractor().getUpcomingMovies(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response: UpcomingResponse ->

                    response.results?.let {
                        movieLiveData.postValue(it.filterNotNull())
                    }

                }, {
                    movieLiveData.value = emptyList()
                })
        )
    }

}