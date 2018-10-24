package br.brilhante.gustavo.feednews.api

import br.brilhante.gustavo.moviesearch.models.UpcomingResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.GET

interface ServerCalls {
    @GET("/movie/upcoming")
    fun desafioNews(
        @Field("api_key") api_key: String,
        @Field("language") language: String,
        @Field("page") page: Int
    ): Single<UpcomingResponse>
}