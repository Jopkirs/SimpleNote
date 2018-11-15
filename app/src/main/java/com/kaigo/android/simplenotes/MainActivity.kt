package com.kaigo.android.simplenotes

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.R.attr.fragment
import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kaigo.android.simplenotes.notefragment.NotesFragment
import com.kaigo.android.simplenotes.signinfragment.SignInFragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var mTextView: TextView
    private var mIsUserSignedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content_main_fragment_container, NotesFragment()).commit()
            nav_view.setCheckedItem(R.id.nav_notes)
            title = nav_view.checkedItem!!.title
        }

        //Widgets
        mTextView = findViewById(R.id.content_main_text_view)

        //Firebase
        auth = FirebaseAuth.getInstance()




    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            auth.signInAnonymously()
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            mTextView.visibility = View.INVISIBLE
                            mIsUserSignedIn = true
                        }
                    }
        }else{
            mIsUserSignedIn = true
            mTextView.visibility = View.INVISIBLE
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var fragment: Fragment
        when (item.itemId) {
            R.id.nav_notes -> {
                fragment = NotesFragment()
                title = item.title
            }
            R.id.nav_sign_in -> {
                fragment = SignInFragment()
                title = item.title
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.content_main_fragment_container, fragment).commit()
        item.isChecked = true

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
