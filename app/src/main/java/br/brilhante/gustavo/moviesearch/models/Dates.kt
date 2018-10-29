package br.brilhante.gustavo.moviesearch.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Dates(

    @field:SerializedName("maximum")
    val maximum: String? = null,

    @field:SerializedName("minimum")
    val minimum: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(maximum)
        writeString(minimum)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Dates> = object : Parcelable.Creator<Dates> {
            override fun createFromParcel(source: Parcel): Dates = Dates(source)
            override fun newArray(size: Int): Array<Dates?> = arrayOfNulls(size)
        }
    }
}