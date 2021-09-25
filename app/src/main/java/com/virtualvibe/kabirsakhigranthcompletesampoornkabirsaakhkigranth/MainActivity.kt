package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.Splitter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spinChapter = findViewById<View>(R.id.spinChapter) as Spinner
        val adapter = ArrayAdapter.createFromResource(this , R.array.chapter_name , android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       spinChapter.adapter = adapter
        spinChapter.onItemSelectedListener = this

        historyButton.setOnClickListener {
            Intent(this@MainActivity , ChapterList::class.java).apply {
                startActivity(this)
            }
        }

        gotoEngDohaEditor.setOnClickListener {
            Intent(this@MainActivity , PostEngLishDohe::class.java).apply {
                startActivity(this)
            }
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text  = parent?.getItemAtPosition(position).toString()
        intentCreator(text)
        if(text == "Select Chapter"){
            Toast.makeText(this , "Please Select Chapter" , Toast.LENGTH_LONG).show()
        }else{
            val regex = "-"
            val selectedChapter = Splitter.splitText(text , regex)[1]
            intentCreator(selectedChapter)
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun intentCreator(c:String){
        next.setOnClickListener {

            if(c == "Select Chapter"){
                Toast.makeText(this , "Please Select Chapter" , Toast.LENGTH_LONG).show()
            }else{
                val regex = "-"
                val chap = Splitter.splitText(c , regex)[0]
                Log.v("err" , "$chap")
                Intent(this , SelectFormat::class.java).apply {
                    this.putExtra("chap_name" , chap)
                    startActivity(this)
                }

            }
        }
    }

}