package com.radiumz.drawer.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.radiumz.drawer.R
import com.radiumz.drawer.adapter.DashboardAdapter

class Dashboard : Fragment() {
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    val nameList = arrayListOf<String>(
        "Abhishek Kumar",
        "Radiumz",
        "Hell yeah",
        "Ak $$33",
        "James",
        "Alpha",
        "Wow!!",
        "Gym",
        "Roller",
        "Hashing",
    )
    lateinit var recyclerAdapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById(R.id.rclrDashboard)

        layoutManager = LinearLayoutManager(activity)

        recyclerAdapter = DashboardAdapter(activity as Context, nameList)

        recyclerDashboard.adapter = recyclerAdapter

        recyclerDashboard.layoutManager = layoutManager

        recyclerDashboard.addItemDecoration(
            DividerItemDecoration(
                recyclerDashboard.context,
                (layoutManager as LinearLayoutManager).orientation
            )
        )

        return view
    }


}