package br.com.alphaaquilae.halloffamebr.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.alphaaquilae.halloffamebr.R
import java.util.ArrayList

/**
 * Created by danielilaro on 06/04/18.
 */

class FavoritosAdapter(activity: Activity, private val favorito: ArrayList<VariaveisFavoritos>?) : ArrayAdapter<VariaveisFavoritos>(activity, 0, favorito) {


    init {
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null
        if (favorito != null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_favoritos, parent, false)
            //pegando o id dos TextView
            val esport_fav = view!!.findViewById<TextView>(R.id.esporte_fav)
            val liga_fav = view.findViewById<TextView>(R.id.lig_fav)
            val time_fav = view.findViewById<TextView>(R.id.time_fav)

            //pegando a posição de cada favorito escolhido
            val fav = favorito[position]

            //setando o valor dos favoritos no TextView
            esport_fav.text = fav.esporte_fav
            liga_fav.text = fav.liga
            time_fav.text = fav.time_fav

        }
        return view!!
    }
}
