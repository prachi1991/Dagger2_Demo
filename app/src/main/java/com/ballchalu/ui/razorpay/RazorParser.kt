package com.ballchalu.ui.razorpay

import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

object RazorParser {

    fun inflateLists(result: String): ArrayList<Pair<String, String>> {
        val list = arrayListOf<Pair<String, String>>()

        try {
            val paymentMethods = JSONObject(result)
            val banksListJSON = paymentMethods.getJSONObject("netbanking")
            banksListJSON.keys().forEach { key ->
                try {
                    list.add(Pair(key, banksListJSON.getString(key)))
                } catch (e: JSONException) {
                    Timber.d("%s", e.message)
                }
            }

        } catch (e: Exception) {
            Timber.e("%s", e.message)
        }
        return list
    }

    fun inflateWalletLists(result: String): ArrayList<String> {
        val list = arrayListOf<String>()
        try {
            val paymentMethods = JSONObject(result)
            val walletListJSON = paymentMethods.getJSONObject("wallet")
            walletListJSON.keys().forEach { key ->
                try {
                    if (walletListJSON.getBoolean(key)) {
                        list.add(key)
                    }
                } catch (e: JSONException) {
                    Timber.d("%s", e.message)
                }
            }

        } catch (e: Exception) {
            Timber.e("%s", e.message)
        }
        return list
    }
}