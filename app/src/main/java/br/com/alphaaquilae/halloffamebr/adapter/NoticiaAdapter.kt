package br.com.alphaaquilae.halloffamebr.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.alphaaquilae.halloffamebr.BD.BancoDados

import com.squareup.picasso.Picasso

import java.util.ArrayList
import br.com.alphaaquilae.halloffamebr.R
import br.com.alphaaquilae.halloffamebr.WebActivity


/**
 * Autor: Daniel Ilaro da Silva
 * Data: 17/02/18.
 */

class NoticiaAdapter(activity: Activity, private val noticias: ArrayList<VariaveisNoticias>?) : ArrayAdapter<VariaveisNoticias>(activity, 0, noticias) {
    var url: String? = null
        private set

    init {
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null
        if (noticias != null) {
            val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.noticias_card, parent, false)

            //pegando o id dos TextView
            val titulo = view!!.findViewById<TextView>(R.id.lblTitulo)
            val imgNoticia = view.findViewById<ImageView>(R.id.imgNoticia)
            val constraiLayout = view.findViewById<ConstraintLayout>(R.id.backNoticias)

            //pegando o banco de dados local
            val bd = BancoDados(view.context)

            //pegando cada noticia colocada no banco de dados remoto
            val noticias2 = noticias[position]

            //setando a url da noticia para abrir na WebView
            url = noticias2.url

            //setando titulo da noticia
            titulo.text = noticias2.titulo

            //Colocando a função de abrir a WebView ao clicar na notícia
            titulo.setOnClickListener {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra("url", getItem(position)!!.url)
                context.startActivity(intent)
            }

            //setando a imagem da noticia
            Picasso.with(context).load(noticias2.img).resize(1000, 500).into(imgNoticia)
            imgNoticia.setOnClickListener {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra("url", getItem(position)!!.url)
                context.startActivity(intent)
            }

            //Pegando do banco de dados local e setando a cor do background de cada notícia
            val res: Cursor? = bd.readCor()
            var cor = " "
            if (res!=null && res.count > 0 ){
                while (res.moveToNext()) {
                    cor = res.getString(0)
                }
            }

            constraiLayout.setBackgroundColor(Color.parseColor(cor))
        }
        return view!!
    }
}
