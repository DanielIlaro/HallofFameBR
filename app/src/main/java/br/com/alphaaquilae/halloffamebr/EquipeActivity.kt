package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class EquipeActivity : AppCompatActivity() {

    lateinit var txtMembros:TextView
    lateinit var txtLink:TextView
    lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipe)

        txtMembros = findViewById(R.id.txtMembros)
        txtLink = findViewById(R.id.txtLink)

        //[configuração do ADS]

        MobileAds.initialize(this, "ca-app-pub-4025526387221161/9617188774")

        mAdView = findViewById<AdView>(R.id.bannerSobre)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //[Fim da ADS]

        val link = "https://blognocostadodazaga.blogspot.com.br/"

        txtMembros.text = "Nós do HOF BR somos uma equipe de apaixonados por esportes, " +
                "cuja ideia é trazer o que nós sabemos para o nosso público de forma séria (ou, às vezes, nem tanto). " +
                "Do futebol da bola redonda à oval, do basquete à Fórmula 1, nosso interesse é trazer informações " +
                "e artigos de qualidade." +
                "\n\nAgora contamos com um parceiro, o blog No Costado da Zaga. Sem essa ajuda não teríamos o Guia de Transmissões. " +
                "O conteúdo do blog é muito bom. Clique no link abaixo e confira. "

        txtLink.text = link
        txtLink.setOnClickListener {
            val wv = WebView(this)
            wv.loadUrl(link)
        }
    }

    override fun onBackPressed(){
        startActivity(Intent(this@EquipeActivity, MainActivity::class.java))
    }
}
