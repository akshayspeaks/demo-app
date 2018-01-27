package com.example.bouch.demo

import com.razorpay.Checkout
import org.json.JSONObject

/**
 * Created by bouch on 27/1/18.
 */
class RazorpayUtil {
    companion object {
        fun getOptions(): JSONObject {
            var options = JSONObject()
            options.put("name", "Akshay")
            options.put("description", "Donation")
            options.put("currency", "INR")
            options.put("amount", "5000")
            return options
        }
    }
}