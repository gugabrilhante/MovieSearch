package br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.holder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.brilhante.gustavo.moviesearch.api.ServerInteractor
import br.brilhante.gustavo.moviesearch.extensions.inflate
import br.brilhante.gustavo.moviesearch.R
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.MovieListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieViewHolder(val parent: ViewGroup, val listener: MovieListener) :
    RecyclerView.ViewHolder(parent.inflate(R.layout.item_movie, false)) {

    fun bind(movie: Movie) {
        itemView.movieTitle.text = movie.title
        itemView.dateTextView.text = String.format(itemView.context.getString(R.string.release_date), movie.releaseDate)

        Glide.with(itemView.context)
            .load(ServerInteractor.IMAGE_BASE_URL + movie.posterPath)
            .into(itemView.movieImageView)

        itemView.setOnClickListener {
            val viewList = listOf(itemView.movieImageView, itemView.movieTitle)
            listener.onMovieClick(movie, viewList)
        }

    }

}