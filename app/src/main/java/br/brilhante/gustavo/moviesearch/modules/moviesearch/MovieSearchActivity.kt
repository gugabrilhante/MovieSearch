package br.brilhante.gustavo.moviesearch.modules.moviesearch

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.extensions.*
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
        savedInstanceState?.let { savedInstance: Bundle ->
            recyclerView.layoutManager?.onRestoreInstanceState(savedInstanceState.getParcelable("LayoutManagerInstance"))
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        recyclerView.layoutManager?.let {
            savedInstanceState.putParcelable(
                "LayoutManagerInstance",
                it.onSaveInstanceState()
            )
        }
        super.onSaveInstanceState(savedInstanceState)
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
        searchView.setOnSearchClickListener {
            toolbar.setBackgroundAnimated(250, R.color.colorPrimary, R.color.white)
        }
        searchView.setOnCloseListener {
            toolbar.setBackgroundAnimated(250, R.color.white, R.color.colorPrimary)
            false
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel?.updateMovieList(searchView.query?.toString())
            swipeRefreshLayout.isRefreshing = true
        }
    }

    private fun registerObservables() {
        registerListMoviesObservable()
        registerHasMovieListSavedObservable()
        registerIsLoadingObservable()
        registerShowErrorMessageObservable()
    }

    private fun registerListMoviesObservable() {
        viewModel?.movieLiveData?.observe(this, Observer { pair: Pair<List<Movie>, Boolean> ->
            adapter.movieList = pair.first
            if (pair.second) {
                saveListOnDatabase(pair.first)
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
        viewModel?.insertListOnDataBase(movies)
    }

    override fun onMovieClick(movie: Movie, viewList: List<View>) {
        val activityOptionsCompat = makeSceneTransitionAnimation(viewList)
        viewModel?.goToMovieDetails(this, activityOptionsCompat, movie)
    }
}
