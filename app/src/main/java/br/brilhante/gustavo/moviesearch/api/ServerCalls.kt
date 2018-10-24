package br.brilhante.gustavo.feednews.api

import br.brilhante.gustavo.moviesearch.models.UpcomingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerCalls {
    @GET("movie/upcoming/")
    fun desafioNews(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<UpcomingResponse>
}