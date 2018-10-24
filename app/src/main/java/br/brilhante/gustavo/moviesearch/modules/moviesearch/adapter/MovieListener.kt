package br.brilhante.gustavo.moviesearch.modules.moviesearch.adapter

import br.brilhante.gustavo.moviesearch.models.Movie

interface MovieListener {
    fun onMovieClick(movie: Movie)
}