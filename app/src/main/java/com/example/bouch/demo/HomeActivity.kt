package com.example.bouch.demo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import com.example.bouch.demo.api.RestAPI
import com.example.bouch.demo.api.TmdbAPI
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxSearchView
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import java.util.Arrays.asList
import java.util.concurrent.TimeUnit


class HomeActivity : AppCompatActivity(), PaymentResultListener {
    private val req_code = 10
    lateinit var searchView: SearchView
    lateinit var rapi:RestAPI
    val auth = FirebaseAuth.getInstance()!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (auth.currentUser != null) {
            // move forward
            showSnackbar("Signed in as " + auth.currentUser!!.phoneNumber)

        } else {
            startSignUpProcess()
        }
        razorpay_button.setOnClickListener {
            Checkout().open(this, RazorpayUtil.getOptions())
        }
        rapi=RestAPI()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setIconifiedByDefault(true)
        RxSearchView.queryTextChanges(searchView)
                .debounce(300,TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .filter { que->que.length>2 }
                .distinctUntilChanged()
                .subscribeOn(AndroidSchedulers.mainThread())
                .switchMap { query->rapi.tmdbApi
                        .searchMovies("8d8405fe915a4416113cc537379e489f",query)}
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { searchResult->

                }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == req_code) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                showSnackbar("Signed in as " + auth.currentUser!!.phoneNumber)
                return
            } else {
                if (response == null) {
                    showSnackbar("Sign in cancelled")
                    return
                }
                if (response.errorCode == ErrorCodes.NO_NETWORK) {
                    showSnackbar("Check internet connection")
                    return
                }
            }
        }
    }

    fun showSnackbar(msg: String) {
        Snackbar.make(findViewById(R.id.home_activity_container), msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        showSnackbar("Payment Unsuccessful")
    }

    override fun onPaymentSuccess(p0: String?) {
        showSnackbar("Payment Received ")
    }

    override fun onBackPressed() {
        if (!searchView.isIconified)
            searchView.isIconified = true
        else
            super.onBackPressed()
    }
}
