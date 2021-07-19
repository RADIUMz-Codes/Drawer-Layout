package com.radiumz.drawer.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.radiumz.drawer.R
import com.radiumz.drawer.adapter.DashboardAdapter
import com.radiumz.drawer.model.Book
import com.radiumz.drawer.util.ConnectionManager

class Dashboard : Fragment() {
//    declaring Recycler View
    lateinit var recyclerDashboard: RecyclerView
//    Layout Manager
    lateinit var layoutManager: RecyclerView.LayoutManager

//   Declaring Adapter
    lateinit var recyclerAdapter: DashboardAdapter

    // Static List
    val bookList= arrayListOf<Book>(
        /*Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)*/
    )
    lateinit var btnDashboard:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

//          Checking for active internet connection
        btnDashboard=view.findViewById(R.id.btnDashboard)
        btnDashboard.setOnClickListener {
            if(ConnectionManager().checkConnectivity(activity as Context)) {
//                Internet is Available

//                Making Dialog Box
                val dialog= AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("OK"){text,listenet->
//                Do Nothing
                }
                dialog.setNegativeButton("Cancel"){ text,listener->
//                    Do nothing
                }
//                Create Dialog
                dialog.create()
//                Show Dialog
                dialog.show()

            }
            else{
//                Internet is Unavailable
                //                Making Dialog Box
                val dialog= AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection is Not Found")
                dialog.setPositiveButton("OK"){text,listenet->
//                Do Nothing
                }
                dialog.setNegativeButton("Cancel"){ text,listener->
//                    Do nothing
                }
//                Create Dialog
                dialog.create()
//                Show Dialog
                dialog.show()

            }
        }

        recyclerDashboard = view.findViewById(R.id.rclrDashboard)

        layoutManager = LinearLayoutManager(activity)


//        Queue variable to store to store a Queue of Requests from an API
        val queue = Volley.newRequestQueue(activity as Context)
//        Url
        val url="http://13.235.250.119/v1/book/fetch_books/"

/*
    Object representation
    Objects created using this syntax are known as
    anonymous objects. We use this syntax when we
    want to override the methods of a class with
    very few modifications.
*/
        if(ConnectionManager().checkConnectivity(activity as Context))
        {
            val jsonObjectRequest=object : JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener {
                    val success =it.getBoolean("success")
                    if(success){
                        val data = it.getJSONArray("data")
                        for(i in 0 until data.length()){
                            val bookJsonObject= data.getJSONObject(i)
                            val bookObject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image")
                            )
                            bookList.add(bookObject)
                            recyclerAdapter = DashboardAdapter(activity as Context, bookList)

                            recyclerDashboard.adapter = recyclerAdapter

                            recyclerDashboard.layoutManager = layoutManager

                            recyclerDashboard.addItemDecoration(
                                DividerItemDecoration(
                                    recyclerDashboard.context,
                                    (layoutManager as LinearLayoutManager).orientation
                                )
                            )
                        }
                    }
                    else{
                        Toast.makeText(activity as Context ,"Error",Toast.LENGTH_SHORT).show()
                    }

                },Response.ErrorListener {
                    print("Error is $it")
                }){

                override fun getHeaders(): MutableMap<String, String> { // this INHERITED class returns mutable maps
                    // this functions returns HashMaps, there won't be any contradictions as it is built on top of MutableMaps
                    val headers= HashMap<String,String>()
                    headers["Content-type"] = "application/json" // 1st key and value
                    headers["token"]="c5011a8786c3f4" // unique token
                    return headers
                }

            }

            queue.add(jsonObjectRequest)
        }
        else{
            val dialog= AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is Not Found")
            dialog.setPositiveButton("Open Setting"){text,listenet->
                // Open setting if internet is unavailable
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)

                // Used this so that when we return back after changing the settings then fragment restarts
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){ text,listener->
                // Close app
                ActivityCompat.finishAffinity(activity as Activity)
            }
//                Create Dialog
            dialog.create()
//                Show Dialog
            dialog.show()

        }

        return view
    }
}