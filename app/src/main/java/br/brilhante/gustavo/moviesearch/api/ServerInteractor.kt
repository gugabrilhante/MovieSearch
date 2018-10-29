package br.brilhante.gustavo.moviesearch.api

import br.brilhante.gustavo.moviesearch.models.MovieInfo
import br.brilhante.gustavo.moviesearch.models.MovieList
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServerInteractor {

    private val serverCalls: ServerCalls

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY = "be811c3c0173e2f4105d0999ef1d02e3"
    }

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

    fun getUpcomingMovies(page: Int): Single<MovieList> {
        return this.serverCalls.upcomingMovies(API_KEY, language, page)
    }

    fun searchMovie(name: String, page: Int): Single<MovieList> {
        return this.serverCalls.searchMovies(API_KEY, language, name, page)
    }

    fun getMovieInfo(movieId: String): Single<MovieInfo> {
        return this.serverCalls.movieInfo(movieId, API_KEY, language)
    }

}