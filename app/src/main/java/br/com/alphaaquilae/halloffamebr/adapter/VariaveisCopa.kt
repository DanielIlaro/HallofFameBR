package br.com.alphaaquilae.halloffamebr.adapter

import com.google.firebase.database.Exclude

/**
 * Created by danielilaro on 20/05/18.
 */

class VariaveisCopa{
    lateinit var nome:String
    var vitorias:Int? = null
    var derrotas:Int? = null
    var empates:Int? = null
    var gols:Int? = null
    var golsSofridos:Int? = null
    var jogos:Int? = null
    var pontos:Int? = null
    var pos:Int? = null
    var sg:Int? = null

    constructor() {


    }


    @Exclude
    fun toMap():Map<String, Any>{
        val result = object:HashMap<String, Any>(){}

        result.put("nome", nome)
        result.put("vitorias", vitorias!!)
        result.put("empates", empates!!)
        result.put("derrotas", derrotas!!)
        result.put("gols", gols!!)
        result.put("golsSofridos", golsSofridos!!)
        result.put("jogos", jogos!!)
        result.put("pontos", pontos!!)
        result.put("pos", pos!!)
        result.put("sg", sg!!)

        return result
    }
}



















