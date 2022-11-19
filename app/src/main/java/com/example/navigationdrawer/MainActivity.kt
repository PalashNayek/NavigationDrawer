package com.example.navigationdrawer

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var actionBar: ActionBar? = null
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private var mFragment: Fragment? = null
    private var mFragmentTransaction: FragmentTransaction? = null
    private var mFragmentManager: FragmentManager? = null
    private var fl_fragment_container: FrameLayout? = null
    private lateinit var drawerLayout: DrawerLayout
    var selectedNavItemIndex: Int = 0
    private val HOME_PAGE = "HomeFragment"
    private val MESSAGE_PAGE = "MessageFragment"
    private val SYNC_PAGE = "SyncFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.nav_view)
        mFragmentManager = supportFragmentManager
        fl_fragment_container = findViewById(R.id.fl_fragment_container)
        mFragment = this.supportFragmentManager.findFragmentById(R.id.fl_fragment_container)
        selectedNavItemIndex = 0
        actionBar = supportActionBar
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        actionBar!!.title = "Home Fragment"
        loadFragment(HOME_PAGE, false, HomeFragment())

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    actionBar!!.title = "Home Fragment"
                    navView.getMenu().getItem(0).setChecked(true);
                    loadFragment(HOME_PAGE, false, HomeFragment())
                    selectedNavItemIndex =
                        0//Toast.makeText(this, "Click home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_message -> {
                    actionBar!!.title = "Message Fragment"
                    navView.getMenu().getItem(1).isChecked = true;
                    loadFragment(MESSAGE_PAGE, false, MessageFragment())
                    selectedNavItemIndex = 1
                }
                R.id.nav_sync -> {
                    actionBar!!.title = "Sync Fragment"
                    navView.getMenu().getItem(2).isChecked = true;
                    loadFragment(SYNC_PAGE, false, SyncFragment())
                    selectedNavItemIndex = 2
                }
                R.id.nav_trash -> {
                    Toast.makeText(this, "Click delete", Toast.LENGTH_SHORT).show()
                    selectedNavItemIndex = 3
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Click settings", Toast.LENGTH_SHORT)
                        .show()
                    selectedNavItemIndex = 4
                }
                R.id.nav_login -> {
                    Toast.makeText(this, "Click login", Toast.LENGTH_SHORT).show()
                    selectedNavItemIndex = 5
                }
                R.id.nav_share -> {
                    Toast.makeText(this, "Click share", Toast.LENGTH_SHORT).show()
                    selectedNavItemIndex = 6
                }
                R.id.nav_rate_us -> {
                    Toast.makeText(this, "Click Rate us", Toast.LENGTH_SHORT).show()
                    selectedNavItemIndex = 7
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadFragment(tag: String, addToStack: Boolean, setFragment: Fragment) {
        mFragmentTransaction = mFragmentManager!!.beginTransaction()
        mFragment = setFragment

        if (addToStack) {
            mFragmentTransaction!!.add(R.id.fl_fragment_container, mFragment!!, tag)
            mFragmentTransaction!!.addToBackStack(tag).commit()

        } else {
            mFragmentTransaction!!.replace(R.id.fl_fragment_container, mFragment!!, tag)
            mFragmentTransaction!!.commit()

        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}