package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import br.com.alphaaquilae.halloffamebr.card.NoticiaAdapter
import br.com.alphaaquilae.halloffamebr.card.VariaveisEventos
import br.com.alphaaquilae.halloffamebr.card.VariaveisNoticias
import br.com.alphaaquilae.halloffamebr.favoritos.NoticiasFavoritas
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_favoritos.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.json.JSONException

class FavoritosActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //variaveis do banco de dados
    lateinit private var mDatabase: DatabaseReference
    lateinit var firebase: ValueEventListener
    var mAuth = FirebaseAuth.getInstance()
    var auth: AccessToken? = null
    var proNoticias: ProgressBar? = null
    lateinit var txtNomeUser: TextView
    lateinit var nav: View

    lateinit var navigation:BottomNavigationView

    //variaveis do projeto
    lateinit var adapter: NoticiaAdapter
    lateinit var arrayNoticias:ArrayList<VariaveisNoticias>

    //variaveis dos objetos do designer
    lateinit var listView: ListView
    lateinit var nf: NoticiasFavoritas
    lateinit var firebase2:ValueEventListener
    lateinit private var mDatabase2: DatabaseReference
    lateinit var arrayEventos: ArrayList<VariaveisEventos>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, container, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        container.addDrawerListener(toggle)
        toggle.syncState()

        mDatabase = FirebaseDatabase.getInstance().getReference().child("noticias")
        listView = findViewById(R.id.listaNoticias)
        proNoticias = findViewById(R.id.proNoticias)
        navigation = findViewById(R.id.navigation)

        navigation.visibility = NavigationView.VISIBLE

        arrayNoticias = ArrayList()
        adapter = NoticiaAdapter(this,arrayNoticias)
        nf = NoticiasFavoritas(this)
        arrayEventos = ArrayList()
        val nav_v:NavigationView = findViewById<NavigationView>(R.id.nav_favoritos)
        nav = nav_v.getHeaderView(0)
        txtNomeUser = nav.findViewById(R.id.txtNomeUser)
        auth = AccessToken.getCurrentAccessToken()

        //lOGIN FACEBOOK
        if(auth!=null){
            val request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken()
            ) { `object`, response ->
                // Application code
                val jsonObject = response.jsonObject


                try {
                    val nom = jsonObject.getString("name")
                    txtNomeUser.text = nom

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            request.executeAsync()


        }

        //[fim login]

        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("eventos")

        //iniciando a pega dos dados do banco de dados de Eventos
        firebase2 = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@FavoritosActivity, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayEventos.clear()

                for (dado in dataSnapshot.children){

                    val novoEvento = dado.getValue(VariaveisEventos::class.java)
                    arrayEventos.add(novoEvento!!)
                }
            }
        }
        mDatabase2.addValueEventListener(firebase2)

        AccessToken.refreshCurrentAccessTokenAsync()
        auth = AccessToken.getCurrentAccessToken()

        //setando o adapter do listView
        listView.adapter = adapter

        listView.setPadding(0,0,0, listView.height+60)

        nav_favoritos.setNavigationItemSelectedListener(this)


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val novoAdapter = nf.procurarNoticias(this, proNoticias!!, "esporte", arrayNoticias, mDatabase, adapter)
                listView.adapter = novoAdapter
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val novoAdapter = nf.procurarNoticias(this, proNoticias!!, "liga", arrayNoticias, mDatabase, adapter)
                listView.adapter = novoAdapter
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val novoAdapter = nf.procurarNoticias(this, proNoticias!!, "time", arrayNoticias, mDatabase, adapter)
                listView.adapter = novoAdapter
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_eventos -> {
                Toast.makeText(this, "Ainda não está pronto, espere a proxima versão", Toast.LENGTH_SHORT).show()
            }
        }
        false
    }

    override fun onBackPressed() {
        if (container.isDrawerOpen(GravityCompat.START)) {
            container.closeDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.nav_favoritos -> {
                if (mAuth.currentUser != null || auth != null) {


                }else{
                    Toast.makeText(this, "Faça o Login na sua Conta do Facebook", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_noticias -> {
                startActivity(Intent(this, MainActivity::class.java))
            }

            R.id.nav_escolher_favoritos -> {
                startActivity(Intent(this, InformacoesActivity::class.java))
            }
            R.id.nav_eventos -> {

                val intent = Intent(this@FavoritosActivity , WebActivity::class.java)
                if (arrayEventos.size != 0){
                    intent.putExtra("url", arrayEventos.get(0).url)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@FavoritosActivity, "Conexão com a internet fraca ou inexistente.", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_equipe -> {
                startActivity(Intent(this@FavoritosActivity, EquipeActivity::class.java))
            }
            R.id.nav_conf -> {
                startActivity(Intent(this@FavoritosActivity, ConfiguracoesActivity::class.java))
            }
        }

        container.closeDrawer(GravityCompat.START)
        return true
    }

}


