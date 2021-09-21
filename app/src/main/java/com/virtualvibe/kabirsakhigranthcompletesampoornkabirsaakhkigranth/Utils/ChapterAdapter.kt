package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.DohaList
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.R
import kotlinx.android.synthetic.main.text_layout_for_recycler.view.*

class ChapterAdapter(c:Context, list:ArrayList<String>) : Adapter<ChapterAdapter.ChapterViewHolder>(){
    val context :Context = c
    var chapList =list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        return ChapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.text_layout_for_recycler , parent , false), context,chapList)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
            when(holder){
                is ChapterViewHolder ->{
                    holder.bind(chapList[position])
                }
            }
    }

    override fun getItemCount(): Int {
         return chapList.size

    }
    class ChapterViewHolder(itemView: View , c:Context , chapList:ArrayList<String>) : RecyclerView.ViewHolder(itemView){
        var doha_text_view = itemView.dohaView
          var context :Context = c

        init {
            doha_text_view.setOnClickListener {
                Intent(context , DohaList::class.java).apply {
                    this.putExtra("chapter" ,chapList[position] )
                    context.startActivity(this)
                }
            }
        }
        fun bind(doha: String) {
                doha_text_view.text = doha
            }
        }
    }

