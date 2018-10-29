package br.brilhante.gustavo.moviesearch.modules.moviedetails

import android.os.Bundle
import androidx.lifecycle.Observer
import br.brilhante.gustavo.moviesearch.api.ServerInteractor
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.extensions.animateAlpha
import br.brilhante.gustavo.moviesearch.extensions.getViewModel
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.modules.base.BaseActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : BaseActivity() {

    private var viewModel: MovieDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        viewModel = getViewModel()
        registerObservables()
        viewModel?.getMovieFromIntent(intent)
    }

    private fun registerObservables() {
        registerMovieObservable()
        registeMovieInfoObservable()
        registerProccessGenreTextObservable()
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

    private fun registeMovieInfoObservable() {
        viewModel?.movieInfoLiveData?.observe(this, Observer {
            overviewTextView.text = it.overview
            overviewTextView.animateAlpha(1f, 150)
            viewModel?.processGenreText(this, it.genres)
        })
    }

    private fun registerProccessGenreTextObservable() {
        viewModel?.genreTextLiveData?.observe(this, Observer {
            genreTextView.text = it
            genreTextView.animateAlpha(1f, 150)
        })
    }

}
