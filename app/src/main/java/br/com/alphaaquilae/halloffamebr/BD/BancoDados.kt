package br.com.alphaaquilae.halloffamebr.BD

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Autor: Daniel Ilaro da Silva
 * Data: 01/03/18.
 */
class BancoDados(context: Context) : SQLiteOpenHelper(context, "Favoritos.db", null, 10) {

    private val NOME_TABELA:String = "Favoritos"
    private val COL_0:String = "COD"
    private val COL_1:String = "NOME"
    private val COL_2:String = "ESPORTE"
    private val COL_3:String = "LIGA"
    private val COL_4:String = "TIME"

    //NovaTabela
    private val TABELA_COR:String = "Cor"
    private val COL_COR_0:String = "id"
    private val COL_COR_1:String = "cor"

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE ${NOME_TABELA} ( ${COL_0} INTEGER PRIMARY KEY AUTOINCREMENT, ${COL_1} TEXT, ${COL_2} TEXT, ${COL_3}  TEXT, ${COL_4} TEXT)")
        //Nova Tabela
        db.execSQL("CREATE TABLE ${TABELA_COR} (${COL_COR_0} INTEGER PRIMARY KEY AUTOINCREMENT, ${COL_COR_1} TEXT)")
        //inserir dados na tabela cor
        val corInicial:String = "#006400"
        db.execSQL("INSERT INTO ${TABELA_COR}(${COL_COR_1}) VALUES ('${corInicial}')")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p1 < 9){
            db!!.execSQL("DROP TABLE IF EXISTS  ${TABELA_COR} ")
            //Nova Tabela
            db.execSQL("CREATE TABLE ${TABELA_COR} (${COL_COR_0} INTEGER PRIMARY KEY AUTOINCREMENT, ${COL_COR_1} TEXT)")

            val corInicial:String = "#006400"
            db.execSQL("INSERT INTO ${TABELA_COR}(${COL_COR_1}) VALUES ('${corInicial}')")
        }
    }

    fun updateCor(cor:String):Boolean{
        val db:SQLiteDatabase= this.writableDatabase
        val  cv = ContentValues()

        cv.put(COL_COR_1, cor)

        val result = db.update(TABELA_COR, cv,"${COL_COR_0} = ?", arrayOf("1"))
        db.close()
        if (result.toInt() == -1){
            return false
        }else{
            return true
        }
    }

    fun readCor(): Cursor{
        val db:SQLiteDatabase = this.writableDatabase
        val res:Cursor = db.rawQuery("SELECT ${COL_COR_1} FROM ${TABELA_COR}",null )
        return res
    }

    fun inserirDados(nome:String, esporte:String?, liga:String?, time:String?): Boolean {
        val db:SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_1, nome)
        cv.put(COL_2, esporte)
        cv.put(COL_3, liga)
        cv.put(COL_4, time)
        val resultado = db.insert(NOME_TABELA, null, cv)
        db.close()

        if (resultado.toInt() == -1){
            return false
        }else{
            return true
        }
    }

    fun readDados(): Cursor {
        val db:SQLiteDatabase = this.writableDatabase
        val res:Cursor = db.rawQuery("SELECT ${COL_1},${COL_2}, ${COL_3}, ${COL_4}  FROM ${NOME_TABELA}", null)
        return res
    }

}