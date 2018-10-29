package br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter

import android.view.View
import br.brilhante.gustavo.moviesearch.models.Movie

interface MovieListener {
    fun onMovieClick(movie: Movie, viewList: List<View>)
}