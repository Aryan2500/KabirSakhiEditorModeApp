package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.Doha
import kotlinx.android.synthetic.main.activity_post_eng_lish_dohe.*

class PostEngLishDohe : AppCompatActivity() {
    var ref : DatabaseReference = FirebaseDatabase.getInstance().reference.child("EngLishDohe")
    lateinit var listener : ValueEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_eng_lish_dohe)

        saveEngDoha.setOnClickListener {
            if(engLishDoha.text.toString() == "" || engLishDohaBhavarth.text.toString() == ""){
                Toast.makeText(this , "Please Enter Content" , Toast.LENGTH_LONG).show()
            }else{
                progressBar2.visibility = View.VISIBLE
                val doha = Doha(engLishDoha.text.toString() , engLishDohaBhavarth.text.toString())
                    ref.push().setValue(doha)

                 listener = object :ValueEventListener{
                     override fun onDataChange(snapshot: DataSnapshot) {
                         Toast.makeText(this@PostEngLishDohe , "Eng Doha Saved" , Toast.LENGTH_LONG).show()
                         progressBar2.visibility = View.GONE
                         engLishDoha.setText("")
                         engLishDohaBhavarth.setText("")
                     }

                     override fun onCancelled(error: DatabaseError) {
                         Toast.makeText(this@PostEngLishDohe , "$error Error occurred" , Toast.LENGTH_LONG).show()
                         progressBar2.visibility = View.GONE
                     }
                 }
                ref.addValueEventListener(listener)
            }
        }
    }
}