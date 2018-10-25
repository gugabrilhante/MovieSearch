package br.brilhante.gustavo.moviesearch.modules.moviesearch

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import br.brilhante.gustavo.feednews.extensions.verticalLinearLayout
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.extensions.getViewModel
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.modules.base.BaseActivity
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.MovieAdapter
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.MovieListener
import kotlinx.android.synthetic.main.activity_movie_search.*

class MovieSearchActivity : BaseActivity(), MovieListener {

    private var viewModel: MovieSearchViewModel? = null

    var adapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)
        viewModel = getViewModel()
        setupViews()
        registerObservables()
        viewModel?.getUpcomingMovieList()
    }

    private fun setupViews() {
        recyclerView.adapter = adapter
        recyclerView.verticalLinearLayout(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel?.searchMovieList(query)
                return false
            }

        })
    }

    private fun registerObservables() {
        registerListMoviesObservables()
    }

    private fun registerListMoviesObservables() {
        viewModel?.movieLiveData?.observe(this, Observer { movieList: List<Movie>? ->
            movieList?.let {
                adapter.movieList = it
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        viewModel?.goToMovieDetails(this, movie)
    }
}
