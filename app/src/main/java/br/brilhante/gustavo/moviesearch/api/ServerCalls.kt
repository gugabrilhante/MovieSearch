package br.brilhante.gustavo.feednews.api

import br.brilhante.gustavo.moviesearch.models.MovieInfo
import br.brilhante.gustavo.moviesearch.models.MovieList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerCalls {
    @GET("movie/upcoming/")
    fun upcomingMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<MovieList>

    @GET("search/movie/")
    fun searchMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") name: String,
        @Query("page") page: Int
    ): Single<MovieList>

    @GET("movie/{movie_id}")
    fun movieInfo(
        @Path("movie_id") movieId: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Single<MovieInfo>

}