package com.example.alicja.pracadyplomowa3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log.d
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loginBtn.setOnClickListener{
            val password = passwordTb.text.toString()
            d("alicja", "Password is $password")

            if (password == "test123"){ //dodać pobranie z JSON'a danych - sprawdzenie isActive karty oraz poprawności hasła
            d("alicja", "Password is correct")
                startActivity(Intent(this, MainMenuActivity::class.java))
            }
            else{
                d("alicja","Password is incorrect")
                Snackbar.make(loginLayout,"Niepoprawny numer karty lub hasło",Snackbar.LENGTH_INDEFINITE).show()
            }
        }

        registerBtn.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            //d("alicja","Registration is unavailable")
            //Snackbar.make(loginLayout, "Unavailable", Snackbar.LENGTH_INDEFINITE).show()
        }
    }
}
