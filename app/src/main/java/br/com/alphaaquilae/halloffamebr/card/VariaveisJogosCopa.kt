package br.com.alphaaquilae.halloffamebr.card

import com.google.firebase.database.Exclude

/**
 * Created by danielilaro on 22/05/18.
 */

class VariaveisJogosCopa(){
    lateinit var data:String
    lateinit var selecao1:String
    lateinit var selecao2:String
    lateinit var bandeira1:String
    lateinit var bandeira2:String
    var golSelecao1:Int? = null
    var golSelecao2:Int? = null



    @Exclude
    fun toMap():Map<String, Any>{
        val result = object:HashMap<String, Any>(){}

        result.put("data", data)
        result.put("selecao1", selecao1)
        result.put("selecao2", selecao2)
        result.put("bandeira1", bandeira1)
        result.put("bandeira2", bandeira2)
        return result
    }
}