package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.SelectedFormat
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_select_format.*

class SelectFormat : AppCompatActivity() {
    lateinit var chapter_name: String
     var isAnyRadioChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_format)
        next2.isEnabled = false
        val extra = intent.extras
        if (extra != null) {
            chapter_name = extra.getString("chap_name").toString()
        }

         radio1.setOnCheckedChangeListener { buttonView, isChecked ->
           if(isChecked){
               radio2.isChecked = false
               radio3.isChecked = false
               isAnyRadioChecked = true
               enablebutton(isAnyRadioChecked)
               SelectedFormat.format = "format1"
           }
        }
         radio2.setOnCheckedChangeListener { buttonView, isChecked ->
             if(isChecked){
                 radio1.isChecked = false
                 radio3.isChecked = false
                 isAnyRadioChecked = true
                 enablebutton(isAnyRadioChecked)
                 SelectedFormat.format = "format2"
             }

        }
        radio3.setOnCheckedChangeListener { buttonView, isChecked ->
           if(isChecked){
               radio1.isChecked = false
               radio2.isChecked = false
               isAnyRadioChecked = true
               enablebutton(isAnyRadioChecked)
               SelectedFormat.format = "format3"
           }
        }

        goToNextActivity()

    }

    fun enablebutton(status :Boolean){
        next2.isEnabled = status
    }

    fun goToNextActivity(){
        next2.setOnClickListener {
            Intent(this , CreatePost::class.java).apply {
                this.putExtra("chap_name" , chapter_name)
                startActivity(this)
            }

        }
    }



}