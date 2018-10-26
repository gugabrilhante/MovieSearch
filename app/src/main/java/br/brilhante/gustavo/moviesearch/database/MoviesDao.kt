package br.brilhante.gustavo.moviesearch.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.brilhante.gustavo.moviesearch.models.Movie
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieList:List<Movie>)

    @Query("SELECT * FROM Movie")
    fun getMovieList(): Single<List<Movie>>

}