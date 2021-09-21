package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.CheckVaniSaved
import kotlinx.android.synthetic.main.activity_preview.*


class Preview : AppCompatActivity() {
    val ref = FirebaseDatabase.getInstance().reference
    var ref1 = ref.child("sakhi_granth_chapter_list")
    lateinit var chapter: String
    var doheList: ArrayList<String> = ArrayList()
    var chapterList: ArrayList<String> = ArrayList()
    lateinit var listener : ValueEventListener
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        progressBar.visibility = View.VISIBLE
        saveData.visibility = View.GONE

        val extras = intent.extras
        if (extras != null) {
            doheList = extras.getStringArrayList("dohaList")!!
            chapter = extras.getString("chapter")!!.trim()
            title_preview.text = "$chapter (Preview)"
            createDohaList(doheList)
        }
//        Log.v("doha rev" , "${doheList[0]}")
        listener = object:   ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {
                    Log.v("snap", " ${it.value.toString()}")
                    chapterList.add(it.value.toString())
                }
                progressBar.visibility = View.GONE
                saveData.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) = Unit

        }
        ref1.addValueEventListener(listener)


        saveData.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            for (dohe in doheList) {
                Log.v("doha rev", "$dohe")
                ref.child("sakhi_granth").child(chapter).push().setValue(dohe)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Vanis Saved Successfully", Toast.LENGTH_LONG).show()
                        saveData.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        CheckVaniSaved.isVaniSaved = true

                    }.addOnFailureListener {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                    }

                Handler().postDelayed({}, 5000)
            }
            var isMatchfound = false
            if(chapterList.isEmpty()){
                ref1.push().setValue(chapter).addOnCompleteListener {
                    this.finish()
                }
            }else{
                chapterList.forEach {
                    Log.v("its" , "$it")
                    if (it == chapter) {
                       isMatchfound = true
                        Log.v("it" , "$isMatchfound")
                    }
                }
                if(!isMatchfound){
                    ref1.push().setValue(chapter).addOnCompleteListener {
                        this.finish()
                    }
                }

            }


        }
    }


    private fun createDohaList(doheList: ArrayList<String>) {
        val adapter: ArrayAdapter<*> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, doheList)

        doha_list.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        ref1.removeEventListener(listener)
    }


}