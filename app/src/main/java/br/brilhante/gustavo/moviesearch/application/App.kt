package br.brilhante.gustavo.moviesearch.application

import android.app.Application
import br.brilhante.gustavo.moviesearch.database.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getAppDataBase(this)
    }
}