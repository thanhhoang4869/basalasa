package com.example.basalasa.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.basalasa.fragment.CategoryFragment
import com.example.basalasa.fragment.HomeFragment
import com.example.basalasa.fragment.ProfileFragment
import com.example.basalasa.fragment.SettingsFragment
import com.example.basalasa.R
import com.example.basalasa.model.GetAccountResponse
import com.example.basalasa.model.entity.Account
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val categoryFragment = CategoryFragment()
        val settingsFragment = SettingsFragment()

        setCurrentFragment(homeFragment)

        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_bar)
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_settings -> processSettings(this, settingsFragment)
                R.id.menu_profile -> setCurrentFragment(profileFragment)
                R.id.menu_home -> setCurrentFragment(homeFragment)
                R.id.menu_category -> setCurrentFragment(categoryFragment)
            }
            true
        }
    }

    private fun processSettings(context: Context, fragment: Fragment) {
        val token = Cache.getToken(context)
        if (token === null) {
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
            finish()
        }

        val response = MyAPI.getAPI().getAccount(token.toString())

        response.enqueue(object : Callback<GetAccountResponse> {
            override fun onResponse(
                call: Call<GetAccountResponse>,
                response: Response<GetAccountResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode == 0) {
                        setCurrentFragment(fragment)
                    }
                }
            }

            override fun onFailure(call: Call<GetAccountResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}