package br.brilhante.gustavo.moviesearch.modules.moviedetails

import android.arch.lifecycle.Observer
import android.os.Bundle
import br.brilhante.gustavo.feednews.api.ServerInteractor
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.extensions.getViewModel
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.modules.base.BaseActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : BaseActivity() {

    var viewModel: MovieDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        viewModel = getViewModel()
        registerObservables()
        viewModel?.getMovieFromIntent(intent)
    }

    private fun registerObservables() {
        registerMovieObservable()
    }

    private fun registerMovieObservable() {
        viewModel?.movieLiveData?.observe(this, Observer { movie: Movie? ->
            movie?.let {
                titleTextView.text = it.title
                Glide.with(this)
                    .load(ServerInteractor.IMAGE_BASE_URL + it.posterPath)
                    .into(movieImageView)
            }
        })
    }
}
