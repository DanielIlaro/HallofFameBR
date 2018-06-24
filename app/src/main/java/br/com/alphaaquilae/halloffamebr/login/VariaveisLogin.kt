package br.com.alphaaquilae.halloffamebr.login

import com.google.firebase.database.Exclude
import java.util.HashMap

/**
 * Created by danielilaro on 14/03/18.
 */
class VariaveisLogin{
    lateinit var nomeAcesso:String
    lateinit var senha:String

    constructor(){

    }

    constructor(nomeAcesso:String, senha:String){
        this.nomeAcesso = nomeAcesso
        this.senha = senha
    }

    @Exclude
    fun toMap():Map<String, Any>{
        val result = HashMap<String, Any>()
        result.put("nomeAcesso", nomeAcesso)
        result.put("senha", senha)

        return result
    }
}