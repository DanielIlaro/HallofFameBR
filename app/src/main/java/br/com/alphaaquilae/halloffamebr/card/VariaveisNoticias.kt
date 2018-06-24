package br.com.alphaaquilae.halloffamebr.card

import com.google.firebase.database.Exclude
import java.util.HashMap

/**
 * Autor: Daniel Ilaro da Silva
 * Data: 16/02/18.
 */
class VariaveisNoticias{
    lateinit var titulo:String
    lateinit var url:String
    var tag1: String? = null
    var tag2: String? = null
    var tag3: String? = null
    lateinit var img: String


    constructor(){

    }

    constructor(ti:String, img:String){
        this.titulo = ti
        this.img = img
    }

    @Exclude
    fun toMap():Map<String, Any>{
        val result = object:HashMap<String,Any>(){}
        result.put("titulo", titulo)
        result.put("url", url)
        result.put("tag1", tag1!!)
        result.put("tag2", tag2!!)
        result.put("tag3", tag3!!)
        result.put("img", img)
        return result
    }
}