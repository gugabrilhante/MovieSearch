package br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import br.brilhante.gustavo.feednews.api.ServerInteractor
import br.brilhante.gustavo.feednews.extensions.inflate
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.MovieListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieViewHolder(val parent: ViewGroup, val listener: MovieListener) :
    RecyclerView.ViewHolder(parent.inflate(R.layout.item_movie, false)) {

    fun bind(movie: Movie) {
        itemView.movieTitle.text = movie.title
        itemView.dateTextView.text = movie.releaseDate

        Glide.with(itemView.context)
            .load(ServerInteractor.IMAGE_BASE_URL + movie.posterPath)
            .into(itemView.movieImageView)

    }

}