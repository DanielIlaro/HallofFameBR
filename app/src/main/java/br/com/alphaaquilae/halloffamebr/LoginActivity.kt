package br.com.alphaaquilae.halloffamebr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.alphaaquilae.halloffamebr.login.ContaLogin
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONException
import com.facebook.GraphRequest
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth

/**
 * Autor: Daniel Ilaro da Silva
 * Data: 19/02/18.
 */

class LoginActivity : AppCompatActivity() {

    private var loginButton: LoginButton? = null
    private lateinit var btnAcessar: Button
    private var callbackManager: CallbackManager? = null
    var nombre = ""
    var id = ""
    var entrou = false
    lateinit var auth: FirebaseAuth
    lateinit var cl: ContaLogin
    //text
    lateinit var txtNomeAcesso: EditText
    lateinit var txtSenha: EditText
    lateinit var txtCriarConta: TextView
    lateinit var mAdView : AdView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //iniciando as variaveis
        txtCriarConta = findViewById(R.id.txtCriarConta)
        btnAcessar = findViewById(R.id.btnSairConta)
        cl = ContaLogin()
        txtNomeAcesso = findViewById(R.id.txtNomeAcesso)
        txtSenha = findViewById(R.id.txtSenha)

        //[configuração do ADS]

        MobileAds.initialize(this, "ca-app-pub-4025526387221161/2385566017")

        mAdView = findViewById<AdView>(R.id.bannerLogin)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //[Fim da ADS]

        //Login com o facebook
        callbackManager = CallbackManager.Factory.create()
        auth = FirebaseAuth.getInstance()
        loginButton = findViewById(R.id.login_button)

        if (auth.currentUser != null) {
            setContentView(R.layout.login_feito)

            val btnSairConta: Button = findViewById(R.id.btnSairConta)

            btnSairConta.setOnClickListener {
                auth.signOut()
                setContentView(R.layout.activity_login)
                onRestart()
            }
        }


        loginButton!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                loginResult.recentlyGrantedPermissions
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
                entrou = true
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Cancela", Toast.LENGTH_SHORT).show()
                entrou = false
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, "Erro", Toast.LENGTH_SHORT).show()
                entrou = false
            }
        })


        //criar conta
        txtCriarConta.setOnClickListener {
            startActivity(Intent(this, NovaContaActivity::class.java))
        }

        //login pelo Hall of Fame
        btnAcessar.setOnClickListener {
            cl.loginConta(this, this, txtNomeAcesso.text.toString(), txtSenha.text.toString())
            if (auth.currentUser != null) {
                nombre = auth.currentUser!!.email!!.substring(0, auth.currentUser!!.email!!.indexOf("@", 0, false)).toUpperCase()
                goMainScreen()
            }
        }

    }

    private fun goMainScreen() {
        val intent = Intent(this, InformacoesActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("nome", nombre)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        if (entrou) {
            val request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken()
            ) { `object`, response ->
                // Application code
                val jsonObject = response.jsonObject


                try {
                    nombre = jsonObject.getString("name")
                    id = jsonObject.getString("id")

                    Toast.makeText(this@LoginActivity, nombre, Toast.LENGTH_SHORT).show()

                    goMainScreen()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            request.executeAsync()
        } else {

        }
    }

    override fun onRestart() {
        super.onRestart()
        //iniciando as variaveis
        txtCriarConta = findViewById(R.id.txtCriarConta)
        btnAcessar = findViewById(R.id.btnSairConta)
        cl = ContaLogin()
        txtNomeAcesso = findViewById(R.id.txtNomeAcesso)
        txtSenha = findViewById(R.id.txtSenha)


        //Login com o facebook
        callbackManager = CallbackManager.Factory.create()
        auth = FirebaseAuth.getInstance()
        loginButton = findViewById(R.id.login_button)

        loginButton!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                loginResult.recentlyGrantedPermissions
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
                entrou = true
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Cancela", Toast.LENGTH_SHORT).show()
                entrou = false
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, "Erro", Toast.LENGTH_SHORT).show()
                entrou = false
            }
        })


        //criar conta
        txtCriarConta.setOnClickListener {
            startActivity(Intent(this, NovaContaActivity::class.java))
        }

        //login pelo Hall of Fame
        btnAcessar.setOnClickListener {
            cl.loginConta(this, this, txtNomeAcesso.text.toString(), txtSenha.text.toString())
            if (auth.currentUser != null) {
                nombre = auth.currentUser!!.email!!.substring(0, auth.currentUser!!.email!!.indexOf("@", 0, false)).toUpperCase()
                goMainScreen()
            }
        }

    }
    override fun onBackPressed(){
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }
}
