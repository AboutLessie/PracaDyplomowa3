package com.example.alicja.pracadyplomowa3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //tutaj PHP do update'a

        //po updat
        registerSaveBtn.setOnClickListener {
            d("alicja","Zarejestrowano! Cofnij do strony logowania")
        }
    }
}
