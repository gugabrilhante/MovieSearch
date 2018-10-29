package br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import br.brilhante.gustavo.moviesearch.models.Movie
import br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter.holder.MovieViewHolder

class MovieAdapter(val listener: MovieListener) : RecyclerView.Adapter<MovieViewHolder>() {

    var movieList: List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MovieViewHolder {
        return MovieViewHolder(parent, listener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }
}