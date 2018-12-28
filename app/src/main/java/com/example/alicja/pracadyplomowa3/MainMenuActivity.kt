package com.example.alicja.pracadyplomowa3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        logOutBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
