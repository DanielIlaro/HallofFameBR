package br.com.alphaaquilae.halloffamebr.adapter

import com.google.firebase.database.Exclude

//Será Continuado depois, dia de parada 23/04/2018
/**
 * Created by danielilaro on 09/04/18.
 */
class VariaveisEventos{
  /*  lateinit var titulo:String
    lateinit var horario:String
    lateinit var data:String
    lateinit var esporte:String
    lateinit var aovivoEm:String*/
    lateinit var url:String

    @Exclude
    fun toMap():Map<String, Any>{
        val result = object:HashMap<String, Any>(){}

        result.put("url", url)
     /*   result.put("titulo", titulo)
        result.put("esporte", esporte)
        result.put("aovivoEm", aovivoEm)
        result.put("data", data)
        result.put("horario", horario)*/

        return result
    }
}