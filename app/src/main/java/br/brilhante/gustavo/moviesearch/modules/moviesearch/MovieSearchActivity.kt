package br.brilhante.gustavo.moviesearch.modules.moviesearch

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import br.brilhante.gustavo.feednews.extensions.verticalLinearLayout
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.extensions.buildAlertDialog
import br.brilhante.gustavo.moviesearch.extensions.getViewModel
import br.brilhante.gustavo.moviesearch.extensions.makeSceneTransitionAnimation
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.modules.base.BaseActivity
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.MovieAdapter
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.MovieListener
import kotlinx.android.synthetic.main.activity_movie_search.*

class MovieSearchActivity : BaseActivity(), MovieListener {

    private var viewModel: MovieSearchViewModel? = null

    private var adapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)
        viewModel = getViewModel()
        setupViews()
        registerObservables()
        viewModel?.checkForSavedMovieList()
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
        swipeRefreshLayout.setOnRefreshListener {
            viewModel?.updateMovieList(searchView.query?.toString())
            swipeRefreshLayout.isRefreshing = true
        }
    }

    private fun registerObservables() {
        registerDownloadListMoviesObservable()
        registerListMoviesObservable()
        registerHasMovieListSavedObservable()
        registerIsLoadingObservable()
        registerShowErrorMessageObservable()
    }

    private fun registerDownloadListMoviesObservable() {
        viewModel?.downloadMovieLiveData?.observe(this, Observer { movieList: List<Movie>? ->
            movieList?.let {
                adapter.movieList = it
                saveListOnDatabase(it)
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun registerListMoviesObservable() {
        viewModel?.databaseMovieLiveData?.observe(this, Observer { movieList: List<Movie>? ->
            movieList?.let {
                adapter.movieList = it
            }
        })
    }

    private fun registerHasMovieListSavedObservable() {
        viewModel?.hasMovieListSavedLiveData?.observe(this, Observer {
            if (!it) {
                viewModel?.getUpcomingMovieList()
            }
        })
    }

    private fun registerIsLoadingObservable() {
        viewModel?.isLoadingLiveData?.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
    }

    private fun registerShowErrorMessageObservable() {
        viewModel?.showErrorMessageLiveDatabase?.observe(this, Observer {
            buildAlertDialog(this, "Error", it).show()
            viewModel?.checkForSavedMovieList()
        })
    }

    private fun saveListOnDatabase(movies: List<Movie>) {
        viewModel?.insertListOnDataBase(this, movies)
    }

    override fun onMovieClick(movie: Movie, viewList: List<View>) {
        val activityOptionsCompat = makeSceneTransitionAnimation(viewList)
        viewModel?.goToMovieDetails(this, activityOptionsCompat, movie)
    }
}
