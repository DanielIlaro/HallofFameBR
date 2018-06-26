package br.com.alphaaquilae.halloffamebr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import br.com.alphaaquilae.halloffamebr.adapter.*
import com.google.firebase.database.*
import android.widget.LinearLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView


class MataMataActivity : AppCompatActivity() {
    lateinit private var mDatabase: DatabaseReference
    lateinit var firebase: ValueEventListener
    lateinit var arrayListaJogos:ArrayList<VariaveisMata_Mata>
    lateinit var adapter: RecyclerView_Mata_Mata
    lateinit var rcView: RecyclerView
    lateinit var txtTituloMataMata:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mata_mata)
        arrayListaJogos = ArrayList()
        adapter = RecyclerView_Mata_Mata(arrayListaJogos, applicationContext)
        rcView = findViewById(R.id.rcView)
        txtTituloMataMata = findViewById(R.id.txtTituloMataMata)

        val layoutManager = LinearLayoutManager(applicationContext)
        rcView.setLayoutManager(layoutManager)
        rcView.setHasFixedSize(true)
        rcView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))


        val referencia = intent.getStringExtra("referencia")
        val titulo = intent.getStringExtra("titulo")

        mDatabase =FirebaseDatabase.getInstance().reference.child("copa_mundo_2018/"+referencia)
        txtTituloMataMata.text = titulo

        firebase = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                Toast.makeText(applicationContext, "Canceldo: "+p0.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                arrayListaJogos.clear()
                for (dado in dataSnapshot!!.children) {
                    val novaNoticias = dado.getValue(VariaveisMata_Mata::class.java)
                    arrayListaJogos.add(novaNoticias!!)
                }
                adapter.notifyDataSetChanged()
                rcView.setAdapter(adapter)
            }
        }
        mDatabase.addValueEventListener(firebase)
    }
}
