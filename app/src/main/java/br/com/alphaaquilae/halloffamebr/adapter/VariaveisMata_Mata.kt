package br.com.alphaaquilae.halloffamebr.adapter

import com.google.firebase.database.Exclude

/**
 * Created by danielilaro on 26/05/18.
 */
class VariaveisMata_Mata(){
    lateinit var data:String
    lateinit var time1:String
    lateinit var time2:String
    lateinit var bandeira1:String
    lateinit var bandeira2:String
    var pontos1:Int? = null
    var pontos2:Int? = null


    @Exclude
    fun toMap():Map<String, Any>{
        val result = object:HashMap<String, Any>(){}

        result.put("data", data)
        result.put("selecao1", time1)
        result.put("selecao2", time2)
        result.put("bandeira1", bandeira1)
        result.put("bandeira2", bandeira2)

        return result
    }
}