package br.com.alphaaquilae.halloffamebr.favoritos

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.widget.ProgressBar
import android.widget.Toast
import br.com.alphaaquilae.halloffamebr.BD.BancoDados
import br.com.alphaaquilae.halloffamebr.adapter.NoticiaAdapter
import br.com.alphaaquilae.halloffamebr.adapter.VariaveisNoticias
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * Autor: Daniel Ilaro da Silva
 * Data: 01/03/18.
 */

class NoticiasFavoritas(context: Context){
    //variaveis do banco de dados
    lateinit var firebase:ValueEventListener
    lateinit var arrayEsportes: ArrayList<String>
    lateinit var arrayTimes:ArrayList<String>
    lateinit var arrayJogadores:ArrayList<String>
    lateinit var nome: String

    var mAuth = FirebaseAuth.getInstance()

    //SQLite
    var db:BancoDados = BancoDados(context)

    fun definirFavoritos(nome:String, esporte:String, liga:String, time:String){
        db.inserirDados(nome, esporte, liga, time)
    }

    fun procurarNoticias(context: Activity, proBar:ProgressBar, fav:String , arrayNoticias:ArrayList<VariaveisNoticias>,
                         mDatabase:DatabaseReference,  adapter:NoticiaAdapter ): NoticiaAdapter{
        arrayEsportes = ArrayList()
        arrayJogadores = ArrayList()
        arrayTimes = ArrayList()

        if (mAuth.currentUser != null){
            nome = mAuth.currentUser!!.email!!.substring(0,mAuth.currentUser!!.email!!.indexOf("@", 0, false)).toUpperCase()
        }
        else{
            val res:Cursor? = db.readDados()

            if (res!=null && res.count > 0){
                res.moveToLast()
                this.nome = res.getString(0)
            }
        }
        //ler banco de dados SQLite
        val res:Cursor? = db.readDados()

        if (res!=null && res.count > 0 ){
            while (res.moveToNext()){
                if (res.getString(0).equals(nome)) {
                    arrayEsportes.add(res.getString(1))
                    arrayTimes.add(res.getString(2))
                    arrayJogadores.add(res.getString(3))
                }
            }
            proBar.visibility = ProgressBar.INVISIBLE

        }

        //iniciando a pega dos dados do banco de dados
         firebase = object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayNoticias.clear()

                for (dado in dataSnapshot.children){

                    val novaNoticias = dado.getValue(VariaveisNoticias::class.java)
                    var n=0
                    while (n < arrayEsportes.size) {
                        if (novaNoticias!!.tag3 == arrayJogadores.get(n) && fav.equals("time")) {
                            arrayNoticias.add(novaNoticias)
                            break
                        }

                        else if (novaNoticias.tag2 == arrayTimes.get(n) && fav.equals("liga")) {
                            arrayNoticias.add(novaNoticias)
                            break
                        }

                        else if (novaNoticias.tag1 == arrayEsportes.get(n) && fav.equals("esporte")) {
                            arrayNoticias.add(novaNoticias)
                            break
                        }
                        n++
                    }

                }
                arrayNoticias.reverse()
                adapter.notifyDataSetChanged()
            }
        }

        mDatabase.addValueEventListener(firebase)
        return adapter
    }


}