package br.com.alphaaquilae.halloffamebr.dialogos

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import br.com.alphaaquilae.halloffamebr.LouchActivity
import br.com.alphaaquilae.halloffamebr.R


/**
 * Created by danielilaro on 28/04/18.
 */
class DSemWifi{
    fun iniciarDialogo(context: Context, alerta:Int){
        val view = View.inflate(context, R.layout.dialogo_wifi, null)
        val alertDialog = AlertDialog.Builder(context)

        if(alerta == 1) {
            view.findViewById<Button>(R.id.btn_wifi).setOnClickListener { context.startActivity(Intent(context, LouchActivity::class.java)) }

            alertDialog.setView(view)
            alertDialog.setCancelable(false)
            alertDialog.create().show()
        } else
        {
            view.findViewById<Button>(R.id.btn_wifi).setOnClickListener { context.startActivity(Intent(context, LouchActivity::class.java)) }

            val titulo = view.findViewById<TextView>(R.id.txtTituloWifi)
            val body = view.findViewById<TextView>(R.id.txtBodyWifi)

            titulo.text = "Conexão Fraca"
            body.text ="Infelizmente não podemos carregar as informações sem um conexão Wifi segura"

            alertDialog.setView(view)
            alertDialog.setCancelable(false)
            alertDialog.create().show()
        }
    }

}