package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.DohaAdapter
import kotlinx.android.synthetic.main.activity_doha_list.*
import kotlinx.android.synthetic.main.activity_history.dohaRecycler

class DohaList : AppCompatActivity() {
    lateinit var chapter :String
    var ref = FirebaseDatabase.getInstance().reference
    lateinit var dohaAdapter : DohaAdapter
    lateinit var dohaList :ArrayList<String>
    lateinit var  listener: ValueEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doha_list)
        dohaList = ArrayList()


        var extra = intent.extras
        if(extra !=null){
            chapter = extra.getString("chapter").toString()
            chapter_name.text = chapter
            Toast.makeText(this , "$chapter" , Toast.LENGTH_LONG).show()
        }
        initRecyclerView()
        loadData()
    }

    private fun loadData() {
        ref = ref.child("sakhi_granth").child(chapter)
        listener = object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                val shot = snapshot.children.toList()
                shot.forEach {
                    dohaList.add(it.getValue().toString())
                }
                dohaAdapter = DohaAdapter( dohaList)
                dohaRecycler.adapter = dohaAdapter

                dohaAdapter.notifyDataSetChanged()
//                Log.v("dohas", "$chapterList")
                if(shot.isEmpty()){
                    Toast.makeText(this@DohaList , "Data Not Available" , Toast.LENGTH_LONG).show()
                }
                progressBarDohaList.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DohaList , "$error  Data May not be available" , Toast.LENGTH_LONG).show()
            }

        }
        ref.addValueEventListener(listener)
    }

    private fun initRecyclerView( ) {
        val layoutManager = LinearLayoutManager(this)
        dohaRecycler.layoutManager = layoutManager

    }

    override fun onDestroy() {
        super.onDestroy()
        ref.removeEventListener(listener)
    }
}