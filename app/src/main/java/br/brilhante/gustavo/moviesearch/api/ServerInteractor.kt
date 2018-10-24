package br.brilhante.gustavo.feednews.api

import br.brilhante.gustavo.moviesearch.models.UpcomingResponse
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServerInteractor {

    private val serverCalls: ServerCalls

    private val BASE_URL = "https://api.themoviedb.org/3"

    private val API_KEY = "be811c3c0173e2f4105d0999ef1d02e3"

    // TODO get languague from the phone
    private val language = "en-US"

    init {
        val clientBuilder = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        serverCalls = retrofit
            .create(ServerCalls::class.java)
    }

    fun getUpcomingMovies(page: Int): Single<UpcomingResponse> {
        return this.serverCalls.desafioNews(API_KEY, language, page)
    }
}