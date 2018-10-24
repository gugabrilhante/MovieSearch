package br.brilhante.gustavo.moviesearch.modules.moviesearch

import android.arch.lifecycle.Observer
import android.os.Bundle
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.extensions.getViewModel
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.modules.BaseActivity
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.MovieAdapter
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.MovieListener

class MovieSearchActivity : BaseActivity(), MovieListener {

    private var viewModel: MovieSearchViewModel? = null

    var adapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)
        viewModel = getViewModel()
    }

    private fun registerObservables() {
        registerListMoviesObservables()
    }

    private fun registerListMoviesObservables() {
        viewModel?.movieLiveData?.observe(this, Observer {
            
        })
    }

    override fun onMovieClick(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
