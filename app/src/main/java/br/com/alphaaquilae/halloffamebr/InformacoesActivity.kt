package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.view.View
import android.widget.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import br.com.alphaaquilae.halloffamebr.BD.BancoDados
import br.com.alphaaquilae.halloffamebr.favoritos.NoticiasFavoritas
import br.com.alphaaquilae.halloffamebr.login.ContaLogin
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.firebase.auth.FirebaseAuth

/**
 * Autor: Daniel Ilaro da Silva
 * Data: 28/02/18.
 */

class InformacoesActivity : AppCompatActivity() {
    //variaveis
    lateinit var nome: String
    lateinit var extra: Bundle
    lateinit var arrayList: ArrayList<String>
    lateinit var arrayLigas:ArrayList<String>
    lateinit var arrayTimes:ArrayList<String>
    lateinit var tipo:String
    lateinit var obj:JSONObject
    lateinit var auth: FirebaseAuth


    //variaveis Interface
    lateinit var txtNome:TextView
    lateinit var txtView5:TextView
    lateinit var spiEsportes:Spinner
    lateinit var spiLigas:Spinner
    lateinit var spiTimes:Spinner
    lateinit var btnConfirmar:Button
    lateinit var btnPular:Button
    lateinit var btnFavoritos:Button
    lateinit var nombre:String

    val cl = ContaLogin()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes)

        //instanciando variaveis
        txtNome = findViewById(R.id.txtNome)
        spiEsportes = findViewById(R.id.spiEsporte)
        spiLigas = findViewById(R.id.spiLigas)
        spiTimes = findViewById(R.id.spiJogadores)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnPular = findViewById(R.id.btnPular)
        btnFavoritos = findViewById(R.id.btnFavoritos)
        txtView5 = findViewById(R.id.textView5)
        nome = " "

        auth = FirebaseAuth.getInstance()
        //instanciando Arrays
        arrayList = ArrayList()
        arrayLigas = ArrayList()
        arrayTimes = ArrayList()

        //pegando o nome do login pelo facebook
        if(intent.hasExtra("nome")) {
            extra = intent.extras
            nome = extra.getString("nome")

            if (lerDados(nome)){
                btnPular.setOnClickListener {
                Toast.makeText(this,"Você tem que ter pelo menos um favorito", Toast.LENGTH_SHORT).show()
            }
            }

            nomeUsuario(nome)
        }
        else if (auth.currentUser != null){
            nombre = auth.currentUser!!.email!!.substring(0,auth.currentUser!!.email!!.indexOf("@", 0, false)).toUpperCase()
            nomeUsuario(nombre)
            nome = nombre

        }
        else{
            var nom = " "

            val request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken()
            ) { `object`, response ->
                // Application code
                val jsonObject = response.jsonObject


                try {
                    nom = jsonObject.getString("name")

                    nomeUsuario(nom)


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            request.executeAsync()

            nome = nom

        }

        //setando o texto da parte de cima da pagina


        //carrega os esportes escrito no Json
        beginJsonParsing("esportes", "favoritos.json", "Nome", arrayList)

        val favoritos = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayList)
        spiEsportes.adapter = favoritos
        trocaLiga()

        //registrar a troca de esportes
        spiEsportes.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                trocaLiga()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        })
        beginJsonParsing(tipo, tipo+".json" ,"Equipes", arrayTimes)

        spiLigas.setOnItemSelectedListener(object : OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //pass
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                arrayTimes.removeAll(arrayTimes)
                beginJsonParsing(tipo, tipo+".json" ,"Equipes", arrayTimes)
                val jogadores = ArrayAdapter(this@InformacoesActivity, android.R.layout.simple_dropdown_item_1line, arrayTimes)
                spiTimes.adapter = jogadores

            }

        })

        val jogadores = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayTimes)
        spiTimes.adapter = jogadores

        btnConfirmar.setOnClickListener {
            passarDados(nome,spiEsportes.selectedItem.toString(), spiLigas.selectedItem.toString(), spiTimes.selectedItem.toString())
            startActivity(Intent(this@InformacoesActivity, MainActivity::class.java))
        }

        if(!lerDados(nome)){
            btnPular.setOnClickListener{
            startActivity(Intent(this@InformacoesActivity, MainActivity::class.java))
        }
        }

        btnFavoritos.setOnClickListener{
            startActivity(Intent(this, MeusFavoritosActivity::class.java))
        }
    }
    fun beginJsonParsing(banco:String,bd:String, dados:String, array: ArrayList<String>){
        try {
            val json = JSONObject(loadJSON(bd))
            val jArray:JSONArray = json.getJSONArray(banco)
            var i = 0
            while (i < jArray.length()){
                try {
                    obj = jArray.getJSONObject(i)
                    val str:String = obj.getString(dados)
                    if (dados == "Equipes"){
                        val st:JSONArray = obj.getJSONArray("Equipes")
                        var x=0
                        while (x < st.length()) {
                            if(i == spiLigas.selectedItemPosition){
                                array.add(st.getString(x))
                            }
                            x+=1
                        }
                    }
                    else{
                        array.add(str)
                    }
                    

                    i++
                }catch (e:JSONException){
                    e.printStackTrace()
                }

            }
        }catch (e:JSONException){
            e.printStackTrace()
        }
    }

    fun loadJSON(bd:String): String? {
        var jsn:String? = null
        try {
            jsn = this.assets.open(bd).bufferedReader().use {
                it.readText()
            }
        }catch (ex:IOException){
            ex.printStackTrace()
            return null
        }
        return jsn
    }

    fun passarDados(nome: String,esporte:String,liga:String, time:String ){
        val nf = NoticiasFavoritas(this)
        nf.definirFavoritos(nome, esporte, liga, time)
    }

    fun trocaLiga(){
        arrayLigas.removeAll(arrayLigas)
        beginJsonParsing("ligas_${spiEsportes.selectedItem}","ligas_${spiEsportes.selectedItem}.json", "Nome", arrayLigas)
        tipo = "ligas_${spiEsportes.selectedItem}"

        if(spiEsportes.selectedItem== "Automobilismo"){
            txtView5.text = "Qual a sua escuderia favorita?"
        }
        val times = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayLigas)
        spiLigas.adapter = times

    }

    fun lerDados(nome:String):Boolean{

        val db = BancoDados(this)
        val res:Cursor? = db.readDados()

        if (res!=null && res.count > 0){
            while (res.moveToNext()){
                if (res.getString(0).equals(nome)){
                    return false
                }
            }
        }
        return true
    }

    fun nomeUsuario(nome:String){
        txtNome.text = nome
    }

    override fun onBackPressed(){
        startActivity(Intent(this@InformacoesActivity, MainActivity::class.java))
    }
}
