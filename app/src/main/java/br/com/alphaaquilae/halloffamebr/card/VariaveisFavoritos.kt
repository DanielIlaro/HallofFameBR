package br.com.alphaaquilae.halloffamebr.card

/**
 * Created by danielilaro on 06/04/18.
 */
class VariaveisFavoritos{
    lateinit var esporte_fav:String
    lateinit var liga:String
    lateinit var time_fav:String

    constructor(){

    }
    constructor(esporte_fav:String, liga_fav:String, time_fav:String) {
        this.esporte_fav = esporte_fav
        this.liga = liga_fav
        this.time_fav = time_fav
    }


}