package com.radiumz.drawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem


import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val actionbar = supportActionBar

        actionbar?.setDisplayHomeAsUpEnabled(true)




        navigationView.setNavigationItemSelectedListener {
            it.isChecked=true
            when (it.itemId) {
                R.id.dashboard -> {
                    Toast.makeText(this, "Nav", Toast.LENGTH_SHORT).show()
                    replaceFragment(Dashboard(),it.title.toString())
                }
                R.id.favorites -> {
                    Toast.makeText(this, "Fav", Toast.LENGTH_SHORT).show()
                    replaceFragment(Favorites(),it.title.toString())
                }
                R.id.profile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    replaceFragment(Profile(),it.title.toString())
                }
                R.id.about -> {
                    Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                    replaceFragment(About(),it.title.toString())
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun replaceFragment(fragment: Fragment,title:String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.addToBackStack(title)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

}