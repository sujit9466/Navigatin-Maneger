package com.example.nevigaionmaneger

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.nevigaionmaneger.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

         //  setSupportActionBar(binding.toolbar)

        setUpNavigationMenu()

        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, AllMatchesFragment())
            .commit()

    }

    private fun setUpNavigationMenu() {

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        toggle.drawerArrowDrawable.color = Color.WHITE

        binding.drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        navigationView = findViewById(R.id.navigation_view)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation item clicks here
            when (menuItem.itemId) {
                R.id.navAllMatches -> {
                    Toast.makeText(this@MainActivity, "${menuItem.title}", Toast.LENGTH_SHORT)
                        .show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, AllMatchesFragment()).commit()

                }

                R.id.navSavedMatches -> {
                    Toast.makeText(this@MainActivity, "${menuItem.title}", Toast.LENGTH_SHORT)
                        .show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, SavedMatchesFragment()).commit()
                }

                else -> {
                    // Handle other item clicks if needed
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }


    }


}