package br.com.alphaaquilae.halloffamebr.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.alphaaquilae.halloffamebr.R
import com.squareup.picasso.Picasso

/**
 * Created by danielilaro on 26/05/18.
 */
class RecyclerView_Mata_Mata:RecyclerView.Adapter<RecyclerView_Mata_Mata.ViewHolder>{
    lateinit var listaJogos:ArrayList<VariaveisMata_Mata>
    lateinit var cont:Context

    constructor(listJogos: ArrayList<VariaveisMata_Mata>, context:Context){
        this.listaJogos = listJogos
        this.cont = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_mata_mata, parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaJogos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.data.text = listaJogos.get(position).data
        holder.selecaoP.text = listaJogos.get(position).time1
        holder.selecaoS.text = listaJogos.get(position).time2
        Picasso.with(cont).load(listaJogos.get(position).bandeira1).into(holder.bandeiraP)
        Picasso.with(cont).load(listaJogos.get(position).bandeira2).into(holder.bandeiraS)

        if (listaJogos.get(position).pontos1.toString() != "null" && listaJogos.get(position).pontos2.toString() != "null"){
            holder.ponto1.text = listaJogos.get(position).pontos1.toString()
            holder.ponto2.text = listaJogos.get(position).pontos2.toString()
        }
    }


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var data = item.findViewById<TextView>(R.id.txtDataMata_Mata)
        var selecaoP = item.findViewById<TextView>(R.id.txtSelecaoP)
        var selecaoS = item.findViewById<TextView>(R.id.txtSelecaoS)
        var bandeiraP = item.findViewById<ImageView>(R.id.bandeiraP)
        var bandeiraS = item.findViewById<ImageView>(R.id.bandeiraS)
        var ponto1 =  item.findViewById<TextView>(R.id.txtMatMat_ponto1)
        var ponto2 = item.findViewById<TextView>(R.id.txtMatMat_ponto2)
    }
}