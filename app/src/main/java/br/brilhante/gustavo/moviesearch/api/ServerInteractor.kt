package br.brilhante.gustavo.feednews.api

import br.brilhante.gustavo.moviesearch.models.UpcomingResponse
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServerInteractor {

    private val serverCalls: ServerCalls

    companion object {
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }

    private val BASE_URL = "https://api.themoviedb.org/3/"

    private val API_KEY = "be811c3c0173e2f4105d0999ef1d02e3"

    private val ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTgxMWMzYzAxNzNlMmY0MTA1ZDA5OTllZjFkMDJlMyIsInN1YiI6IjViY2ZiMjQwYzNhMzY4NjYzZDAyMzAwZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Eo2DDq4MXWmAlHgyFXl3oXf4BMX22JR_prZDAJSUOKM"

    // TODO get languague from the phone
    private val language = "en-US"

    init {
        val clientBuilder = OkHttpClient.Builder()

//        addTokenInterceptor(clientBuilder)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        serverCalls = retrofit
            .create(ServerCalls::class.java)
    }

    private fun addTokenInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val tokenInterceptor = Interceptor { chain ->
            var newRequest = chain.request()
            newRequest = newRequest.newBuilder().addHeader("Authorization", "Token $ACCESS_TOKEN").build()
            chain.proceed(newRequest)
        }
        builder.addNetworkInterceptor(tokenInterceptor)
        return builder
    }

    fun getUpcomingMovies(page: Int): Single<UpcomingResponse> {
        return this.serverCalls.desafioNews(API_KEY, language, page)
    }
}