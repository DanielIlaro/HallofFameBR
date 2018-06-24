package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

/**
 * Autor: Daniel Ilaro da Silva
 * Data: 25/02/18.
 */

class LouchActivity : AppCompatActivity(), Runnable {
    override fun run() {
        startActivity(object :Intent(this, MainActivity::class.java){})
        finish()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_louch)

        val hr = Handler()
        hr.postDelayed(this, 3000)
    }
}
