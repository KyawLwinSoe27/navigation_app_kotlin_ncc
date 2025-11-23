package com.example.navigationapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navigationapp.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val id = bundle?.getInt("id")
        val username = bundle?.getString("username")
        val firstName = bundle?.getString("firstName")
        val lastName = bundle?.getString("lastName")

        Log.d("UserActivity", "Received user data: id=$id, username=$username, firstName=$firstName, lastName=$lastName")
        Toast.makeText(this, "Welcome $firstName $lastName!", Toast.LENGTH_LONG).show()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.user_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

//        setupActionBarWithNavController(navController)

        binding.bottomNavView.setupWithNavController(navController)
        navController.navigate(R.id.welcomeUserFragment, bundle)
        binding.bottomNavView.setOnItemSelectedListener {
            item ->
            when(item.itemId) {
                R.id.itemWelcome -> {
                    navController.navigate(R.id.welcomeUserFragment, bundle)
                    true
                }
                R.id.itemEntry -> {
                    navController.navigate(R.id.entryFragment, bundle)
                    true
                }
                R.id.itemListView -> {
                    navController.navigate(R.id.listViewFragment, bundle)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val result = when(item.itemId) {
            R.id.itemLogout -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> false
        }
        return result
    }
}