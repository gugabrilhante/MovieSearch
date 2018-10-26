package br.brilhante.gustavo.moviesearch.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.brilhante.gustavo.moviesearch.models.Movie
import io.reactivex.Flowable

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieList:List<Movie>):List<Long>

    @Query("SELECT * FROM Movie")
    fun getMovieList(): Flowable<List<Movie>>

}