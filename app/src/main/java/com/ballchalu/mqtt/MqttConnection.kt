package com.ballchalu.mqtt

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ballchalu.utils.network.NetworkUtil
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber
import java.text.MessageFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttConnection @Inject constructor(
    val context: Context,
    val mqttAndroidClient: MqttAndroidClient?,
    private val mqttConnectOptions: MqttConnectOptions?
) : LifecycleObserver {
    private var client: MqttAndroidClient? = null
    private var appStatus = true

    fun connectToClient() {
        if (NetworkUtil.getConnectivityStatus(context) == 0) return
        if (appStatus)
            createConnection()
    }

    private fun createConnection() {
        try {
            mqttAndroidClient?.connect(mqttConnectOptions, context, object : IMqttActionListener {
                override fun onSuccess(mqttToken: IMqttToken) {
                    Timber.e("Client connected")
                    val message = MqttMessage("Hello, I am Android Mqtt Client.".toByteArray())
                    message.qos = 2
                    message.isRetained = false
                    try {
                        mqttAndroidClient.subscribe(Companion.topic, 0)
                        context.sendBroadcast(Intent(Companion.mqttAction))
                    } catch (e: MqttException) {
                        Timber.e(e)
                    }
                }

                override fun onFailure(arg0: IMqttToken, arg1: Throwable) {
                    Timber.i(
                        MessageFormat.format(
                            "mqttAndroidClient connection failed: {0}",
                            arg1.message
                        )
                    )
                    connectToClient()
                }
            })
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Timber.e("App in background")
        appStatus = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Timber.e("App in foreground")
        appStatus = true
    }

    companion object {
        const val topic = "arenas/global"
        const val mqttAction = "mqtt_connection_state"
    }
}