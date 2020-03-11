package com.ballchalu.mqtt

import org.eclipse.paho.android.service.MqttTraceHandler
import timber.log.Timber

class MqttTraceCallback : MqttTraceHandler {
    override fun traceDebug(arg0: String, arg1: String) {
        Timber.tag("MQTT trace $arg0").i(arg1)
    }

    override fun traceError(arg0: String, arg1: String) {
        Timber.tag("MQTT trace $arg0").i(arg1)
    }

    override fun traceException(
        arg0: String, arg1: String,
        arg2: Exception
    ) {
        Timber.tag("MQTT trace $arg0").i(arg1)
    }
}