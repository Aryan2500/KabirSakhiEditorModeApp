package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.R
import kotlinx.android.synthetic.main.text_layout_for_recycler.view.*

class DohaAdapter(list:ArrayList<String>) : RecyclerView.Adapter<DohaAdapter.DohaViewHolder>(){

    var dohaList =list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DohaViewHolder  {
        return DohaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.text_layout_for_recycler , parent , false) )
    }


    override fun onBindViewHolder(holder: DohaAdapter.DohaViewHolder, position: Int) {
        when(holder){
            is DohaViewHolder ->{
                holder.bind(dohaList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dohaList.size

    }
    class DohaViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView){
        var doha_text_view = itemView.dohaView

        fun bind(doha: String) {
            doha_text_view.text = doha
        }
    }

}

