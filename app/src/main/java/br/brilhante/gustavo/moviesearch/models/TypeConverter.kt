package br.brilhante.gustavo.moviesearch.models

import com.google.gson.Gson

class TypeConverter {

    companion object {
        var gson = Gson()

        fun stringToGenreList(data:String?):List<Int?>{
            data?.let {
                return gson.fromJson(data, Array<Int>::class.java).asList()
            } ?: return emptyList()
        }

        fun genreListToString(list: List<Int?>): String{
            return gson.toJson(list)
        }
    }

}