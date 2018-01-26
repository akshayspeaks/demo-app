package com.example.bouch.demo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import java.util.Arrays.asList



class HomeActivity : AppCompatActivity() {

    private val req_code = 10
    val auth = FirebaseAuth.getInstance()!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (auth.currentUser!=null){
            // move forward
            showSnackbar("Signed in as "+ auth.currentUser!!.phoneNumber)

        }
        else{
            startSignUpProcess()
        }
    }

    private fun startSignUpProcess() {
        val providers = asList(
                AuthUI.IdpConfig.PhoneBuilder().build())
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setAvailableProviders(providers)
                        .build(),
                req_code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == req_code){
            /*
                this checks if the activity result we are getting is for the sign in
                as we can have more than activity to be started in our Activity.
             */
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                /*
                    Checks if the User sign in was successful
                 */
//                startActivity(Next Activity)
                showSnackbar("Signed in as "+ auth.currentUser!!.phoneNumber)
//                finish()
                return
            }
            else {
                if(response == null){
                    //If no response from the Server
                    showSnackbar("Sign in cancelled")
                    return
                }
                if(response.errorCode == ErrorCodes.NO_NETWORK){
                    //If there was a network problem the user's phone
                    showSnackbar("Check internet connection")
                    return
                }
            }
        }
    }
    fun showSnackbar(msg : String){
        Snackbar.make(findViewById(R.id.home_activity_container), msg, Snackbar.LENGTH_LONG).show()
    }
}
