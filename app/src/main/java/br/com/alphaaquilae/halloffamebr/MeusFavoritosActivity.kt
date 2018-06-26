package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import br.com.alphaaquilae.halloffamebr.BD.BancoDados
import br.com.alphaaquilae.halloffamebr.adapter.FavoritosAdapter
import br.com.alphaaquilae.halloffamebr.adapter.VariaveisFavoritos
import com.google.firebase.auth.FirebaseAuth

class MeusFavoritosActivity : AppCompatActivity() {

    lateinit var listMeuFavorito:ListView
    lateinit var btnPaginaNoticias:Button
    lateinit var btnAdicionar:Button
    lateinit var listFavoritos: ArrayList<VariaveisFavoritos>
    lateinit var adapter:FavoritosAdapter
    lateinit var nome:String

    var mAuth = FirebaseAuth.getInstance()
    var db = BancoDados(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_favoritos)

        // [Iniciando variaveis]
        listMeuFavorito = findViewById(R.id.listMeuFavorito)
        btnAdicionar = findViewById(R.id.btnAdicionar)
        btnPaginaNoticias = findViewById(R.id.btnPaginaNoticias)

        listFavoritos = ArrayList()

        adapter = FavoritosAdapter(this, listFavoritos)
        //[fim da inicialização]

        if (mAuth.currentUser != null){
            nome = mAuth.currentUser!!.email!!.substring(0,mAuth.currentUser!!.email!!.indexOf("@", 0, false)).toUpperCase()

        }
        else{
            val db = BancoDados(this)
            val res:Cursor? = db.readDados()

            if (res!=null && res.count > 0){
                res.moveToLast()
                this.nome = res.getString(0)
            }
        }
        //ler banco de dados SQLite
        val res: Cursor? = db.readDados()

        if (res!=null && res.count > 0 ) {
            while (res.moveToNext()) {

                if (res.getString(0).equals(nome)){
                    listFavoritos.add(VariaveisFavoritos(res.getString(1), res.getString(2), res.getString(3)))
                }
            }
        }

        listMeuFavorito.adapter = adapter

        btnPaginaNoticias.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnAdicionar.setOnClickListener {
            startActivity(Intent(this@MeusFavoritosActivity, InformacoesActivity::class.java))
        }

    }

    override fun onBackPressed(){
        startActivity(Intent(this@MeusFavoritosActivity, MainActivity::class.java))
    }
}
