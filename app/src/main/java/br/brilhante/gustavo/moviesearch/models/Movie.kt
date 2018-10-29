package br.brilhante.gustavo.moviesearch.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class Movie(

    @field:SerializedName("overview")
    var overview: String? = null,

    @field:SerializedName("original_language")
    var originalLanguage: String? = null,

    @field:SerializedName("original_title")
    var originalTitle: String? = null,

    @field:SerializedName("video")
    var video: Boolean? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("genre_ids")
    @Ignore
    var genreIds: List<Int?>? = null,

    @field:SerializedName("poster_path")
    var posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @field:SerializedName("release_date")
    var releaseDate: String? = null,

    @field:SerializedName("vote_average")
    var voteAverage: Double? = null,

    @field:SerializedName("popularity")
    var popularity: Double? = null,

    @PrimaryKey
    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("adult")
    var adult: Boolean? = null,

    @field:SerializedName("vote_count")
    var voteCount: Int? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readString(),
        ArrayList<Int?>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(overview)
        writeString(originalLanguage)
        writeString(originalTitle)
        writeValue(video)
        writeString(title)
        writeList(genreIds)
        writeString(posterPath)
        writeString(backdropPath)
        writeString(releaseDate)
        writeValue(voteAverage)
        writeValue(popularity)
        writeValue(id)
        writeValue(adult)
        writeValue(voteCount)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}