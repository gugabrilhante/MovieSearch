package br.brilhante.gustavo.moviesearch.modules.base

import androidx.appcompat.app.AppCompatActivity
import br.brilhante.gustavo.moviesearch.utils.DisposableManager

open class BaseActivity : AppCompatActivity() {

    override fun onDestroy() {
        DisposableManager.clear()
        super.onDestroy()
    }
}