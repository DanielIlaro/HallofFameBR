package br.com.alphaaquilae.halloffamebr.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.alphaaquilae.halloffamebr.LoginActivity
import br.com.alphaaquilae.halloffamebr.R
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import org.json.JSONException
import java.util.logging.Handler
import java.util.logging.LogRecord

/**
 * Created by danielilaro on 14/03/18.
 */
class ContaLogin(){
    //variaveis
    var erro:Boolean = false
    var senErro:Boolean = false

    //autenticação
    lateinit var auth: FirebaseAuth

    val TAG:String = "EmailPassword";

    //fazer login
    fun loginConta(context: Context, activity: Activity,nomeAcesso:String, senha:String){
        auth = FirebaseAuth.getInstance()

        if (nomeAcesso.equals("") || senha.equals("")){
            Toast.makeText(context, "Não aceitamos campos em branco", Toast.LENGTH_SHORT).show()
        }else {

            auth.signInWithEmailAndPassword(nomeAcesso, senha).addOnCompleteListener(activity, object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.getCurrentUser()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException())
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            })


            if (auth.currentUser != null) {
                activity.setContentView(R.layout.login_feito)

                val btnSairConta: Button = activity.findViewById(R.id.btnSairConta)

                btnSairConta.setOnClickListener {
                    auth.signOut()
                    activity.setContentView(R.layout.activity_login)
                }
            }
        }
    }

    fun analiseSenha(senha1:String, senha2:String) : String{
        if (senha1.equals(senha2)){
            senErro = false
            return " "
        }
        else if (senha2 == ""){
            senErro = true
            return " "
        }
        else{
            senErro = true
        }

        return "Digite as senhas iguais"
    }

    fun gravarNovaConta(context: Context, activity: Activity, usuario:String,senha:String){
        auth = FirebaseAuth.getInstance()

        if (senErro or erro){
            Toast.makeText(context, "Concerte seu erro", Toast.LENGTH_SHORT).show()
        }
        if (usuario.equals("") || senha.equals("")){
            Toast.makeText(context, "Não é permitido valores em branco", Toast.LENGTH_SHORT).show()
        }
        else{
            //mDatabase.setValue(VariaveisLogin(usuario,senha))
            auth.createUserWithEmailAndPassword(usuario, senha)
            Toast.makeText(context, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()

            context.startActivity(Intent(context, LoginActivity::class.java))

        }
    }


}