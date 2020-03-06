package com.ballchalu.mqtt

import android.content.Intent
import com.ballchalu.BuildConfig
import com.ballchalu.application.App
import com.ballchalu.utils.network.NetworkUtil
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber
import java.text.MessageFormat

object MqttConnection {
    private var client: MqttAndroidClient? = null
    const val topic = "arenas/global"
    const val mqttAction = "mqtt_connection_state"

    fun connectToClient() {
        if (NetworkUtil.getConnectivityStatus(App.instance) == 0) return
        val conOpt = MqttConnectOptions()
        val clientId = App.instance.androidId
        val port = 1883
        val uri: String
        uri = "${BuildConfig.mqttUrl}:$port"
        client = MqttAndroidClient(App.instance, uri, clientId)
        val clientHandle = uri + clientId
        val username = BuildConfig.mqttUserName
        val password = BuildConfig.mqttPassword
        val actionArgs = arrayOfNulls<String>(1)
        actionArgs[0] = clientId
        conOpt.isCleanSession = false
        conOpt.userName = username
        conOpt.password = password.toCharArray()
        client?.setCallback(MqttCallbackHandler(App.instance, clientHandle))
        client?.setTraceCallback(MqttTraceCallback())
        try {
            client?.connect(conOpt, null, object : IMqttActionListener {
                override fun onSuccess(mqttToken: IMqttToken) {
                    Timber.e("Client connected")
                    val message = MqttMessage("Hello, I am Android Mqtt Client.".toByteArray())
                    message.qos = 2
                    message.isRetained = false
                    try {
                        client?.subscribe(topic, 0)
                        App.instance.sendBroadcast(Intent(mqttAction))
                    } catch (e: MqttException) {
                        Timber.e(e)
                    }
                }

                override fun onFailure(arg0: IMqttToken, arg1: Throwable) {
                    Timber.i(MessageFormat.format("Client connection failed: {0}", arg1.message))
                    connectToClient()
                }
            })
        } catch (e: MqttException) {
            Timber.e(e)
        }
    }
}