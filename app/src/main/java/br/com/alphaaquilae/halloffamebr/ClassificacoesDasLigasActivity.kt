package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_tabela.*

class ClassificacoesDasLigasActivity : AppCompatActivity() {
    lateinit var btnCopaDoMundo:Button
    lateinit var btnOitavas:Button
    lateinit var btnQuartas:Button
    lateinit var btnSemi:Button
    lateinit var btnTerceiro:Button
    lateinit var btnFinal:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classificacoes_das_ligas)

        //[Iniciar Variaveis]
        btnCopaDoMundo = findViewById(R.id.btnCopaDoMundo)
        btnOitavas = findViewById(R.id.btnOitavas)
        btnQuartas = findViewById(R.id.btnQuartas)
        btnSemi = findViewById(R.id.btnSemi)
        btnTerceiro = findViewById(R.id.btnTerceiro)
        btnFinal = findViewById(R.id.btnFinal)
        //[Final de Iniciação]

        //click copa do mundo
        btnCopaDoMundo.setOnClickListener{
            intent = Intent(this@ClassificacoesDasLigasActivity, TabelaActivity::class.java)
            intent.putExtra("copa", "Copa do Mundo 2018" )
            startActivity(intent)
        }

        btnOitavas.setOnClickListener { intent = Intent(this@ClassificacoesDasLigasActivity, MataMataActivity::class.java)
            intent.putExtra("referencia", "oitavas_de_final")
            intent.putExtra("titulo", "Oitavas de Final")
            startActivity(intent)
        }
        btnQuartas.setOnClickListener { intent = Intent(this@ClassificacoesDasLigasActivity, MataMataActivity::class.java)
            intent.putExtra("referencia", "quartas_de_final")
            intent.putExtra("titulo", "Quartas de Final")
            startActivity(intent)
        }
        btnSemi.setOnClickListener { intent = Intent(this@ClassificacoesDasLigasActivity, MataMataActivity::class.java)
            intent.putExtra("referencia", "semi_final")
            intent.putExtra("titulo", "Sem-Final")
            startActivity(intent)
        }
        btnTerceiro.setOnClickListener {
            intent = Intent(this@ClassificacoesDasLigasActivity, MataMataActivity::class.java)
            intent.putExtra("referencia", "terceiro_lugar")
            intent.putExtra("titulo", "Terceiro Lugar")
            startActivity(intent)
        }
        btnFinal.setOnClickListener{
            intent = Intent(this@ClassificacoesDasLigasActivity, MataMataActivity::class.java)
            intent.putExtra("referencia", "final")
            intent.putExtra("titulo", "Final")
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this@ClassificacoesDasLigasActivity, MainActivity::class.java))
    }
}
