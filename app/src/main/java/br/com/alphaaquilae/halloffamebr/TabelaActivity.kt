package br.com.alphaaquilae.halloffamebr

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import br.com.alphaaquilae.halloffamebr.card.VariaveisCopa
import br.com.alphaaquilae.halloffamebr.card.VariaveisJogosCopa
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.jogos_copa.*

class TabelaActivity : AppCompatActivity() {

    lateinit private var mDatabase: DatabaseReference
    lateinit var firebase: ValueEventListener

    lateinit var arraySituacao:ArrayList<VariaveisCopa>
    lateinit var arrayJogos:ArrayList<VariaveisJogosCopa>

    lateinit private var mDatabase2: DatabaseReference
    lateinit var firebase2: ValueEventListener

    var a:Int = 0
    var b:Int = 1
    var rodada = 1

    //[Variaveis de Objetos]

    lateinit var row1: TableRow
    lateinit var row2: TableRow
    lateinit var row3: TableRow
    lateinit var row4: TableRow
    lateinit var row5: TableRow

    //[Botões]
    lateinit var btnA:Button
    lateinit var btnB:Button
    lateinit var btnC:Button
    lateinit var btnD:Button
    lateinit var btnE:Button
    lateinit var btnF:Button
    lateinit var btnG:Button
    lateinit var btnH:Button

    //[Labels]
    lateinit var txtDataPartida:TextView
    lateinit var txtDataPartida2:TextView
    lateinit var txtSelecao1:TextView
    lateinit var txtSelecao2:TextView
    lateinit var txtSelecao3:TextView
    lateinit var txtSelecao4:TextView
    lateinit var txtTituloPartida:TextView
    lateinit var txtPontos1:TextView
    lateinit var txtPontos2:TextView
    lateinit var txtPontos3:TextView
    lateinit var txtPontos4:TextView

    //[imagem]
    lateinit var imgBand1:ImageView
    lateinit var imgBand2:ImageView
    lateinit var imgBand3:ImageView
    lateinit var imgBand4:ImageView
    lateinit var imgBtnVoltar:ImageButton
    lateinit var imgBtnProximo:ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabela)

        mDatabase = FirebaseDatabase.getInstance().reference.child("copa_mundo_2018/grupoA")
        mDatabase2 = FirebaseDatabase.getInstance().reference.child("copa_mundo_2018/jogos_grupoA")

        arraySituacao = ArrayList()
        arrayJogos = ArrayList()

        //[Variaveis de Objetos]
        row1 = findViewById(R.id.row1)
        row2 = findViewById(R.id.row2)
        row3 = findViewById(R.id.row3)
        row4 = findViewById(R.id.row4)
        row5 = findViewById(R.id.row5)
        //[Botões]
        btnA = findViewById(R.id.btnA)
        btnB = findViewById(R.id.btnB)
        btnC = findViewById(R.id.btnC)
        btnD = findViewById(R.id.btnD)
        btnE = findViewById(R.id.btnE)
        btnF = findViewById(R.id.btnF)
        btnG = findViewById(R.id.btnG)
        btnH = findViewById(R.id.btnH)
        //[Labels]
        txtTituloPartida = findViewById(R.id.txtTituloPartida)
        txtDataPartida = findViewById(R.id.txtDataPartida)
        txtDataPartida2 = findViewById(R.id.txtDataPartida2)
        txtSelecao1 = findViewById(R.id.txtSelecao1)
        txtSelecao2 = findViewById(R.id.txtSelecao2)
        txtSelecao3 = findViewById(R.id.txtSelecao3)
        txtSelecao4 = findViewById(R.id.txtSelecao4)
        txtPontos1 = findViewById(R.id.ponto1)
        txtPontos2 = findViewById(R.id.ponto2)
        txtPontos3 = findViewById(R.id.ponto3)
        txtPontos4 = findViewById(R.id.pontos4)

        //[imagem]
        imgBand1 = findViewById(R.id.imgBand1)
        imgBand2 = findViewById(R.id.imgBand2)
        imgBand3 = findViewById(R.id.imgBand3)
        imgBand4 = findViewById(R.id.imgBand4)
        imgBtnProximo = findViewById(R.id.imgBtnProximo)
        imgBtnVoltar = findViewById(R.id.imgBtnVoltar)

        //[Fim Variaveis de Objetos]

        //iniciando a pega dos dados do banco de dados de Tabela Copa do Mundo
        firebase = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@TabelaActivity, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arraySituacao.clear()
                for (dado in dataSnapshot.children) {
                    val novaNoticias = dado.getValue(VariaveisCopa::class.java)
                    arraySituacao.add(novaNoticias!!)


                }
                escreverNaLinha(row2, row3, row4, row5)
            }
        }
        mDatabase.addValueEventListener(firebase)

        //[Inicio clicks nos botões]
        btnA.setOnClickListener { mudaMdatabase("copa_mundo_2018/grupoA", "copa_mundo_2018/jogos_grupoA")
            mudaCorBotao(btnA)
        }
        btnB.setOnClickListener { mudaMdatabase("copa_mundo_2018/grupoB", "copa_mundo_2018/jogos_grupoB")
            mudaCorBotao(btnB)
        }
        btnC.setOnClickListener { mudaMdatabase("copa_mundo_2018/grupoC", "copa_mundo_2018/jogos_grupoC")
            mudaCorBotao(btnC)
        }
        btnD.setOnClickListener { mudaMdatabase("copa_mundo_2018/grupoD", "copa_mundo_2018/jogos_grupoD")
            mudaCorBotao(btnD)
        }
        btnE.setOnClickListener { mudaMdatabase("copa_mundo_2018/grupoE", "copa_mundo_2018/jogos_grupoE")
            mudaCorBotao(btnE)
        }
        btnF.setOnClickListener { mudaMdatabase("copa_mundo_2018/grupoF", "copa_mundo_2018/jogos_grupoF")
            mudaCorBotao(btnF)
        }
        btnG.setOnClickListener { mudaMdatabase("copa_mundo_2018/grupoG", "copa_mundo_2018/jogos_grupoG")
            mudaCorBotao(btnG)
        }
        btnH.setOnClickListener { mudaMdatabase("copa_mundo_2018/grupoH", "copa_mundo_2018/jogos_grupoH")
            mudaCorBotao(btnH)
        }
        imgBtnVoltar.setOnClickListener {
            if (rodada > 1){
                rodada--
                a-=2
                b-=2
                mudarJogos(rodada,a, b)
            }
        }
        imgBtnProximo.setOnClickListener{
            if (rodada != 3){
                rodada++
                a+=2
                b+=2
                mudarJogos(rodada,a, b)

            }
        }
        btnA.setBackgroundColor(Color.parseColor("#BFFF00"))
        //[fim clicks nos botões]

        //iniciando a pega dos dados do banco de dados de Jogos da copa
        firebase2 = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@TabelaActivity, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayJogos.clear()
                for (dado in dataSnapshot.children) {
                    val novaNoticias = dado.getValue(VariaveisJogosCopa::class.java)
                    arrayJogos.add(novaNoticias!!)


                }
                mudarJogos(rodada, a, b)
            }
        }
        mDatabase2.addValueEventListener(firebase2)

    }

    fun mudaMdatabase(txt:String, txt2:String){
        mDatabase = FirebaseDatabase.getInstance().reference.child(txt)
        mDatabase.addValueEventListener(firebase)
        mDatabase2 = FirebaseDatabase.getInstance().reference.child(txt2)
        mDatabase2.addValueEventListener(firebase2)

    }

    fun escreverNaLinha(row: TableRow, row2: TableRow, row3: TableRow, row4: TableRow){


        for (a in arraySituacao){

            when(a.pos){
                1 -> {
                    row.removeAllViews()
                    row.addView(criarTexto(a.pos.toString()),0)
                    row.addView(criarTexto(a.nome),1)
                    row.addView(criarTexto(a.pontos.toString()),2)
                    row.addView(criarTexto(a.vitorias.toString()),3)
                    row.addView(criarTexto(a.empates.toString()),4)
                    row.addView(criarTexto(a.derrotas.toString()),5)
                    row.addView(criarTexto(a.gols.toString()),6)
                    row.addView(criarTexto(a.golsSofridos.toString()),7)
                    row.addView(criarTexto(a.sg.toString()),8)
                }

                2 -> {
                    row2.removeAllViews()
                    row2.addView(criarTexto(a.pos.toString()),0)
                    row2.addView(criarTexto(a.nome),1)
                    row2.addView(criarTexto(a.pontos.toString()),2)
                    row2.addView(criarTexto(a.vitorias.toString()),3)
                    row2.addView(criarTexto(a.empates.toString()),4)
                    row2.addView(criarTexto(a.derrotas.toString()),5)
                    row2.addView(criarTexto(a.gols.toString()),6)
                    row2.addView(criarTexto(a.golsSofridos.toString()),7)
                    row2.addView(criarTexto(a.sg.toString()),8)
                }

                3 -> {
                    row3.removeAllViews()
                    row3.addView(criarTexto(a.pos.toString()),0)
                    row3.addView(criarTexto(a.nome),1)
                    row3.addView(criarTexto(a.pontos.toString()),2)
                    row3.addView(criarTexto(a.vitorias.toString()),3)
                    row3.addView(criarTexto(a.empates.toString()),4)
                    row3.addView(criarTexto(a.derrotas.toString()),5)
                    row3.addView(criarTexto(a.gols.toString()),6)
                    row3.addView(criarTexto(a.golsSofridos.toString()),7)
                    row3.addView(criarTexto(a.sg.toString()),8)
                }

                4 -> {
                    row4.removeAllViews()
                    row4.addView(criarTexto(a.pos.toString()),0)
                    row4.addView(criarTexto(a.nome),1)
                    row4.addView(criarTexto(a.pontos.toString()),2)
                    row4.addView(criarTexto(a.vitorias.toString()),3)
                    row4.addView(criarTexto(a.empates.toString()),4)
                    row4.addView(criarTexto(a.derrotas.toString()),5)
                    row4.addView(criarTexto(a.gols.toString()),6)
                    row4.addView(criarTexto(a.golsSofridos.toString()),7)
                    row4.addView(criarTexto(a.sg.toString()),8)
                }
            }
        }
    }

    fun criarTexto(txto:String): TextView {
        val txt = object : TextView(this){}
        txt.text = txto
        txt.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

        return txt
    }

    fun mudarJogos(rodada:Int ,a:Int, b:Int){

        txtTituloPartida.text = "Rodada ${rodada}"

        txtDataPartida.text = arrayJogos.get(a).data
        Picasso.with(applicationContext).load(arrayJogos.get(a).bandeira1).into(imgBand1)
        txtSelecao1.text = arrayJogos.get(a).selecao1
        txtSelecao2.text = arrayJogos.get(a).selecao2

        if(arrayJogos.get(a).golSelecao1.toString() == "null") {
            txtPontos1.text = " "
            txtPontos2.text = " "
        }else{
            txtPontos1.text = arrayJogos.get(a).golSelecao1.toString()
            txtPontos2.text = arrayJogos.get(a).golSelecao2.toString()
        }


        Picasso.with(applicationContext).load(arrayJogos.get(a).bandeira2).into(imgBand2)


        txtDataPartida2.text = arrayJogos.get(b).data
        txtSelecao3.text = arrayJogos.get(b).selecao1
        Picasso.with(applicationContext).load(arrayJogos.get(b).bandeira1).into(imgBand3)
        txtSelecao4.text = arrayJogos.get(b).selecao2

        if(arrayJogos.get(b).golSelecao1.toString() == "null") {
            txtPontos3.text = " "
            txtPontos4.text = " "
        }else{
            txtPontos3.text = arrayJogos.get(b).golSelecao1.toString()
            txtPontos4.text = arrayJogos.get(b).golSelecao2.toString()
        }

        Picasso.with(applicationContext).load(arrayJogos.get(b).bandeira2).into(imgBand4)


    }

    fun mudaCorBotao(btn:Button){
        btnA.setBackgroundColor(Color.parseColor("#009688"))
        btnB.setBackgroundColor(Color.parseColor("#009688"))
        btnC.setBackgroundColor(Color.parseColor("#009688"))
        btnD.setBackgroundColor(Color.parseColor("#009688"))
        btnE.setBackgroundColor(Color.parseColor("#009688"))
        btnF.setBackgroundColor(Color.parseColor("#009688"))
        btnG.setBackgroundColor(Color.parseColor("#009688"))
        btnH.setBackgroundColor(Color.parseColor("#009688"))

        btn.setBackgroundColor(Color.parseColor("#BFFF00"))
    }
}
