package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import br.com.alphaaquilae.halloffamebr.BD.BancoDados
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class ConfiguracoesActivity : AppCompatActivity() {
    lateinit var spiCorFundoNoticias:Spinner
    lateinit var arrayCores:ArrayList<String>
    lateinit var arrayNomeCor:ArrayList<String>

    lateinit var btnConfirmarConf:Button
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        //[Iniciar Variaveis]
        spiCorFundoNoticias = findViewById(R.id.spiCorFundoNoticias)
        arrayCores = arrayListOf("#FFFFFF", "#000000", "#0000FF", "#006400", "#FF00FF", "#FF0000", "#68228B", "#FFD39B", "#90EE90", "#9C9C9C")
        arrayNomeCor = arrayListOf("Branco", "Preto", "Azul", "Verde Escuro", "Magento", "Vermelho", "Orquidea Escura", "Rosa", "Verde Claro", "Cinza")
        btnConfirmarConf = findViewById(R.id.btnConfirmasConf)
        //[Fim do inicio de variaveis]

        //[configuração do ADS]

         MobileAds.initialize(this, "ca-app-pub-4025526387221161/3084388251")

         mAdView = findViewById<AdView>(R.id.banerConf)
         val adRequest = AdRequest.Builder().build()
         mAdView.loadAd(adRequest)

        //[Fim da ADS]

        spiCorFundoNoticias.adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayNomeCor)
        val  bd = BancoDados(this)


        spiCorFundoNoticias.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spiCorFundoNoticias.setBackgroundColor(Color.parseColor(arrayCores.get(spiCorFundoNoticias.selectedItemPosition)))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //pass
            }

        })

        btnConfirmarConf.setOnClickListener {
            bd.updateCor(arrayCores.get(spiCorFundoNoticias.selectedItemPosition))
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onBackPressed(){
        startActivity(Intent(this@ConfiguracoesActivity, MainActivity::class.java))
    }
}
