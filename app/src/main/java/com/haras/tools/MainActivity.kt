package com.haras.tools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCotizador.setOnClickListener{openCotizador()}
        btnIzettle.setOnClickListener{openiZettle()}

    }

    private fun openCotizador(){
        startActivity(Intent(this, Cotizador::class.java))
    }

    private fun openiZettle(){
        startActivity(Intent(this,iZettle::class.java))
    }

}
