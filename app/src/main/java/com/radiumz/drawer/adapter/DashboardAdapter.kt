package com.radiumz.drawer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.radiumz.drawer.R
import com.radiumz.drawer.activity.DescriptionActivity
import com.radiumz.drawer.model.Book
import com.squareup.picasso.Picasso
import java.util.ArrayList

class DashboardAdapter(val context: Context,val itemList: ArrayList<Book>):RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(view:View):RecyclerView.ViewHolder(view){
        val txtBookName:TextView=view.findViewById(R.id.txtBookName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice:TextView= view.findViewById(R.id.txtBookPrice)
        val txtBookRating:TextView= view.findViewById(R.id.txtBookRating)
        val imgBookImage:ImageView =view.findViewById(R.id.imgBookName)
        val rclyItem:LinearLayout=view.findViewById(R.id.rclrItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text=book.bookName
        holder.txtBookAuthor.text=book.bookAuthor
        holder.txtBookPrice.text=book.bookPrice
        holder.txtBookRating.text=book.bookRating
//        holder.imgBookImage.setImageResource(book.bookImage)
//        Using Picasso to populate Image from a url into a view
        Picasso.get().load(book.bookImage).into(holder.imgBookImage)
        holder.rclyItem.setOnClickListener {
            Toast.makeText(context,"Clicked on ${holder.txtBookName.text}",Toast.LENGTH_SHORT).show()
            val intent = Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }
}