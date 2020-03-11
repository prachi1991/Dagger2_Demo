package com.ballchalu.mqtt

import org.json.JSONObject

object Helper {

    fun getSafeStringObjectFromJson(
        jsonObject: JSONObject,
        key: String
    ): String {
        var result = ""
        try {
            if (jsonObject.has(key)) {
                result = jsonObject.getString(key)
                if (result.equals("null", ignoreCase = true)) {
                    result = ""
                }
            }

        } catch (e: Exception) {
            result = ""
            e.printStackTrace()
        }

        return result
    }


}




