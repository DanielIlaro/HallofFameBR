package br.com.alphaaquilae.halloffamebr


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
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
import br.com.alphaaquilae.halloffamebr.dialogos.DSemWifi
import br.com.alphaaquilae.halloffamebr.adapter.NoticiaAdapter
import br.com.alphaaquilae.halloffamebr.adapter.VariaveisEventos
import br.com.alphaaquilae.halloffamebr.adapter.VariaveisNoticias
import br.com.alphaaquilae.halloffamebr.favoritos.NoticiasFavoritas
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.json.JSONException
import java.util.*


/**
 * Autor: Daniel Ilaro da Silva
 * Data: 16/02/18.
 */

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, Runnable {
    override fun run() {
        if(listView.adapter.count <= 0){
            dSemWifi.iniciarDialogo(this,2)
        }
    }

    //variaveis do banco de dados
    lateinit private var mDatabase: DatabaseReference
    lateinit var firebase:ValueEventListener
    lateinit var firebase2:ValueEventListener
    lateinit private var mDatabase2: DatabaseReference
    lateinit var arrayEventos: ArrayList<VariaveisEventos>


    var mAuth: FirebaseAuth? = null
    var auth:AccessToken? = null
    var proNoticias:ProgressBar? = null
    val dSemWifi = DSemWifi()

    //variaveis do projeto
    lateinit var adapter: NoticiaAdapter
    lateinit var arrayNoticias:ArrayList<VariaveisNoticias>

    //variaveis dos objetos do designer
    lateinit var listView: ListView
    lateinit var navigation:BottomNavigationView
    lateinit var nf:NoticiasFavoritas
    lateinit var txtNomeUser:TextView
    lateinit var nav: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val connMgr:ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo:NetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val dadosInfo:NetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val isWifiConn:Boolean = networkInfo.isConnected() or dadosInfo.isConnected()

        //variaves2
        arrayEventos = ArrayList()

        //instanciar variaveis
        val fireOpt = FirebaseOptions.Builder()
        val fireApp = FirebaseApp.initializeApp(applicationContext)
        mAuth = FirebaseAuth(fireApp)


        mDatabase = FirebaseDatabase.getInstance().getReference().child("noticias")
        listView = findViewById(R.id.listaNoticias)
        proNoticias = findViewById(R.id.proNoticias)
        navigation = findViewById(R.id.navigation)
        val nav_v:NavigationView = findViewById(R.id.nav_view) as NavigationView
        nav = nav_v.getHeaderView(0)
        txtNomeUser = nav.findViewById(R.id.txtNomeUser)

        navigation.visibility = NavigationView.INVISIBLE

        arrayNoticias = ArrayList()
        adapter = NoticiaAdapter(this,arrayNoticias)
        nf = NoticiasFavoritas(this)

        AccessToken.refreshCurrentAccessTokenAsync()
        auth = AccessToken.getCurrentAccessToken()

        //setando o adapter do listView
        listView.adapter = adapter

        if (!isWifiConn){
            listView.visibility = ListView.INVISIBLE
            dSemWifi.iniciarDialogo(this, 1)

        }
        else {

            //lOGIN FACEBOOK
            if (auth != null) {
                var nom = " "
                val request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken()
                ) { `object`, response ->
                    // Application code
                    val jsonObject = response.jsonObject


                    try {
                        nom = jsonObject.getString("name")
                        txtNomeUser.text = nom

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
                request.executeAsync()


            }
            val hd = Handler()

            //[V.5.3 Runnable]
            hd.postDelayed(this, 10000)
            //[fim V.5.3]


            //iniciando a pega dos dados do banco de dados de Noticias
            firebase = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@MainActivity, p0.message, Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    arrayNoticias.clear()
                    for (dado in dataSnapshot.children) {
                        val novaNoticias = dado.getValue(VariaveisNoticias::class.java)
                        arrayNoticias.add(novaNoticias!!)
                    }
                    arrayNoticias.reverse()
                    proNoticias!!.visibility = ProgressBar.INVISIBLE
                    adapter.notifyDataSetChanged()
                }
            }
            mDatabase.addValueEventListener(firebase)

            mDatabase2 = FirebaseDatabase.getInstance().getReference().child("eventos")

            //iniciando a pega dos dados do banco de dados de Eventos
            firebase2 = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@MainActivity, p0.message, Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    arrayEventos.clear()

                    for (dado in dataSnapshot.children) {

                        val novoEvento = dado.getValue(VariaveisEventos::class.java)
                        arrayEventos.add(novoEvento!!)
                    }
                }
            }
            mDatabase2.addValueEventListener(firebase2)
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            onStop()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_login -> {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }

            R.id.nav_favoritos -> {
                if (mAuth!!.currentUser != null || auth != null) {
                   startActivity(Intent(this, FavoritosActivity::class.java))

                }else{
                    Toast.makeText(this, "Faça o Login na sua Conta do Facebook ou no App", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_noticias -> {
                mDatabase.addValueEventListener(firebase)
            }

            R.id.nav_escolher_favoritos -> {
                if(mAuth!!.currentUser != null || auth != null) {
                    startActivity(Intent(this@MainActivity, InformacoesActivity::class.java))
                }else{
                    Toast.makeText(this, "Faça o Login na sua Conta do Facebook ou no App", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_eventos -> {

                val intent = Intent(this@MainActivity, WebActivity::class.java)
                if (arrayEventos.size != 0){
                    intent.putExtra("url", arrayEventos.get(0).url)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@MainActivity, "Conexão com a internet fraca ou inexistente.", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_equipe -> {
                startActivity(Intent(this@MainActivity, EquipeActivity::class.java))
            }

            R.id.nav_conf -> {
                startActivity(Intent(this@MainActivity, ConfiguracoesActivity::class.java))
            }

            R.id.nav_classificacao -> {
                startActivity(Intent(this@MainActivity,ClassificacoesDasLigasActivity::class.java) )
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStop() {
        super.onStop()

        //para o banco de dados
        mDatabase.removeEventListener(firebase)
    }


}
