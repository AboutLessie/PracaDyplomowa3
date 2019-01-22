package com.example.alicja.pracadyplomowa3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*


class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        val points : Int = 3
        val pointsToString = points.toString()

        pointsProgress.progress = points
        pointsInfo.text = "Masz $points punktów!"

        when {
            points == 0 -> {
                cappuccinoBox.isChecked = false
                doppioBox.isChecked = false
                americanoBox.isChecked = false
                espressoBox.isChecked = false
                flatBox.isChecked = false
                latteBox.isChecked = false
            }
            points in 10..20 -> {
                cappuccinoBox.isChecked = true
                doppioBox.isChecked = true
                americanoBox.isChecked = false
                espressoBox.isChecked = false
                flatBox.isChecked = false
                latteBox.isChecked = false
            }
            points in 21..30 -> {
                cappuccinoBox.isChecked = true
                doppioBox.isChecked = false
                americanoBox.isChecked = true
                espressoBox.isChecked = true
                flatBox.isChecked = true
                latteBox.isChecked = false
            }
            else -> {
                cappuccinoBox.isChecked = true
                doppioBox.isChecked = true
                americanoBox.isChecked = true
                espressoBox.isChecked = true
                flatBox.isChecked = true
                latteBox.isChecked = true
            }
        }

        logOutBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
