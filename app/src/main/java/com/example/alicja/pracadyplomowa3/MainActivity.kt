package com.example.alicja.pracadyplomowa3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log.d
import kotlinx.android.synthetic.main.activity_main.*
import android.os.AsyncTask.execute


import java.util.ArrayList
import java.util.List

import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import android.app.Activity
import android.app.ProgressDialog
import android.os.AsyncTask
import java.awt.font.OpenType.TAG_NAME
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main_menu.*


class MainActivity : AppCompatActivity() {

        // Progress Dialog
        private ProgressDialog pDialog

        // JSON parser class
        JSONParser jsonParser = new JSONParser()

        // single product url
        private static final String url_user_detials = "https://api.androidhive.info/android_connect/get_product_details.php"

        // url to update product
        private static final String url_update_product = "https://api.androidhive.info/android_connect/update_product.php"

        // url to delete product
        private static final String url_delete_product = "https://api.androidhive.info/android_connect/delete_product.php"

        // JSON Node names
        private static final String TAG_SUCCESS = "success"
        private static final String TAG_USERS = "Users"
        private static final String TAG_CARDID = "cardId"
        private static final String TAG_USERID = "userID"
        private static final String TAG_POINTS = "points"
        private static final String TAG_ISACTIVE = "isActive"
        private static final String TAG_TIMEOFCREATION = "timeOfCreation"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting product details from intent
        val i = intent

        // getting user id (cardId) from intent
        cardId = i.getStringExtra(TAG_CARDID)

        // Getting complete product details in background thread
        GetProductDetails().execute()

        loginBtn.setOnClickListener{
            val passwordNew = passwordTb.text.toString()
            d("alicja", "Password is $passwordNew")

            if (passwordNew == "test123"){
            d("alicja", "Password is correct")
                startActivity(Intent(this, MainMenuActivity::class.java))
            }
            else{
                d("alicja","Password is incorrect")
                Snackbar.make(loginLayout,"Niepoprawny numer karty lub hasło",Snackbar.LENGTH_INDEFINITE).show()
            }
        }

        registerBtn.setOnClickListener{
            d("alicja","Registration is unavailable")
            Snackbar.make(loginLayout, "Unavailable", Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    internal inner class GetProductDetails : AsyncTask<String, String, String>() {

        /**
         * Before starting background thread Show Progress Dialog
         */
        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@MainActivity)
            pDialog.setMessage("Loading user details. Please wait...")
            pDialog.setIndeterminate(false)
            pDialog.setCancelable(true)
            pDialog.show()
        }

        /**
         * Getting product details in background thread
         */
        override fun doInBackground(vararg params: String): String? {

            // updating UI from Background Thread
            runOnUiThread {
                // Check for success tag
                val success: Int
                try {
                    // Building Parameters
                    val params = ArrayList<NameValuePair>()
                    params.add(BasicNameValuePair("cardId", cardId))

                    // getting product details by making HTTP request
                    // Note that product details url will use GET request
                    val json = jsonParser.makeHttpRequest(
                            url_user_detials, "GET", params)

                    // check your log for json response
                    Log.d("Single User Details", json.toString())

                    // json success tag
                    success = json.getInt(TAG_SUCCESS)
                    if (success == 1) {
                        // successfully received user details
                        val productObj = json
                                .getJSONArray(TAG_USERS) // JSON Array

                        // get first user object from JSON Array
                        val product = productObj.getJSONObject(0)

                        // user with this cardId found
                        // Edit Text
                        cardIdTb = findViewById(R.id.inputCardId) as EditText
                        passwordTb = findViewById(R.id.inputPassword) as EditText
                        pointsInfo = findViewById(R.id.inputPoints) as EditText

                        // display user data in EditText
                        cardIdTb.setText(product.getString(TAG_CARDID))
                        passwordTb.setText(product.getString(TAG_PASSWORD))
                        pointsInfo.setText(product.getString(TAG_POINTS))

                    } else {
                        // product with pid not found
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            return null
        }

        /**
         * After completing background task Dismiss the progress dialog
         */
        override fun onPostExecute(file_url: String) {
            // dismiss the dialog once got all details
            pDialog.dismiss()
        }
    }

    /**
     * Background Async Task to  Save product Details
     */
    internal inner class SaveUserDetails : AsyncTask<String, String, String>() {

        /**
         * Before starting background thread Show Progress Dialog
         */
        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@MainActivity)
            pDialog.setMessage("Saving product ...")
            pDialog.setIndeterminate(false)
            pDialog.setCancelable(true)
            pDialog.show()
        }

        /**
         * Saving product
         */
        override fun doInBackground(vararg args: String): String? {

            // getting updated data from EditTexts
            val cardId = txtCardId.getText().toString()
            val password = txtPassword.getText().toString()
            val points = txtPoints.getText().toString()

            // Building Parameters
            val params = ArrayList<NameValuePair>()
            params.add(BasicNameValuePair(TAG_CARDID, cardId))
            params.add(BasicNameValuePair(TAG_USERID, userId))
            params.add(BasicNameValuePair(TAG_PASSWORD, password))
            params.add(BasicNameValuePair(TAG_POINTS, points))
            params.add(BasicNameValuePair(TAG_ISACTIVE, isActive))
            params.add(BasicNameValuePair(TAG_TIMEOFCREATION, timeOfCreation))

            // sending modified data through http request
            // Notice that update product url accepts POST method
            val json = jsonParser.makeHttpRequest(url_update_product,
                    "POST", params)

            // check json success tag
            try {
                val success = json.getInt(TAG_SUCCESS)

                if (success == 1) {
                    // successfully updated
                    val i = intent
                    // send result code 100 to notify about product update
                    setResult(100, i)
                    finish()
                } else {
                    // failed to update product
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * After completing background task Dismiss the progress dialog
         */
        override fun onPostExecute(file_url: String) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss()
        }
    }

    /*****************************************************************
     * Background Async Task to Delete Product
     */
    internal inner class DeleteUser : AsyncTask<String, String, String>() {

        /**
         * Before starting background thread Show Progress Dialog
         */
        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@MainActivity)
            pDialog.setMessage("Deleting Product...")
            pDialog.setIndeterminate(false)
            pDialog.setCancelable(true)
            pDialog.show()
        }

        /**
         * Deleting product
         */
        override fun doInBackground(vararg args: String): String? {

            // Check for success tag
            val success: Int
            try {
                // Building Parameters
                val params = ArrayList<NameValuePair>()
                params.add(BasicNameValuePair("cardId", cardId))

                // getting product details by making HTTP request
                val json = jsonParser.makeHttpRequest(
                        url_delete_product, "POST", params)

                // check your log for json response
                Log.d("Delete Product", json.toString())

                // json success tag
                success = json.getInt(TAG_SUCCESS)
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    val i = intent
                    // send result code 100 to notify about product deletion
                    setResult(100, i)
                    finish()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * After completing background task Dismiss the progress dialog
         */
        override fun onPostExecute(file_url: String) {
            // dismiss the dialog once product deleted
            pDialog.dismiss()

        }

    }
}
