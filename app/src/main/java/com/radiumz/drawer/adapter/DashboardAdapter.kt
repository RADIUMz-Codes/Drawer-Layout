package com.radiumz.drawer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.radiumz.drawer.R
import java.util.ArrayList

class DashboardAdapter(val context: Context,val itemList: ArrayList<String>):RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(view:View):RecyclerView.ViewHolder(view){
        val textView:TextView=view.findViewById(R.id.txtBookName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val text = itemList[position]
        holder.textView.text=text
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }
}