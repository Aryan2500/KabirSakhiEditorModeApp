package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.ChapterAdapter
import kotlinx.android.synthetic.main.activity_doha_list.*
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_history.dohaRecycler

class ChapterList : AppCompatActivity() {
    var ref = FirebaseDatabase.getInstance().reference
    lateinit var chapterAdapter : ChapterAdapter
    lateinit var chapterList :ArrayList<String>
    lateinit var  listener: ValueEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        chapterList = ArrayList()
        initRecyclerView()
        loadData()

    }



    private fun loadData() {
        ref = ref.child("sakhi_granth_chapter_list")
       listener = object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                val shot = snapshot.children.toList()
                    shot.forEach {
                        chapterList.add(it.getValue().toString())
                    }
                    chapterAdapter = ChapterAdapter(  this@ChapterList,chapterList)
                    dohaRecycler.adapter = chapterAdapter
                if(shot.isEmpty()){
                    Toast.makeText(this@ChapterList , "Data Not Available" , Toast.LENGTH_LONG).show()
                }
                chapterAdapter.notifyDataSetChanged()
//                Log.v("dohas", "$chapterList")
                progressBarChapterList.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChapterList , "$error  Data May not be available" , Toast.LENGTH_LONG).show()
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
        ref.addValueEventListener(listener)
    }
}