package br.com.alphaaquilae.halloffamebr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import br.com.alphaaquilae.halloffamebr.login.ContaLogin




class NovaContaActivity : AppCompatActivity() {
    lateinit var edtNomeAcesso:EditText
    lateinit var edtSenha:EditText
    lateinit var edtTestSenha:EditText
    lateinit var txtSituacaoNome:TextView
    lateinit var txtSituacaoSenha:TextView
    lateinit var btnCriarConta:Button



    val cl = ContaLogin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_conta)

        //[iniciando as variaveis]

        edtNomeAcesso = findViewById(R.id.edtNomeAcesso)
        edtSenha = findViewById(R.id.edtSenha)
        edtTestSenha = findViewById(R.id.edtTestSenha)
        txtSituacaoNome = findViewById(R.id.txtSituacaoNome)
        txtSituacaoSenha = findViewById(R.id.txtSituacaoSenha)
        btnCriarConta = findViewById(R.id.btnCriarConta)

        //[fim da iniciação das variaveis]

        edtTestSenha.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                txtSituacaoSenha.text = cl.analiseSenha(edtSenha.text.toString(), p0!!.toString())
            }

        })

        btnCriarConta.setOnClickListener {
            cl.gravarNovaConta(this, this, edtNomeAcesso.text.toString(), edtSenha.text.toString())
        }
    }

    override fun onBackPressed(){
        startActivity(Intent(this@NovaContaActivity, MainActivity::class.java))
    }
}
