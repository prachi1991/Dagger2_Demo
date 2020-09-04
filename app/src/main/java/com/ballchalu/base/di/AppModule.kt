package com.ballchalu.base.di

import android.content.ClipboardManager
import android.content.Context
import android.provider.Settings
import com.ballchalu.BuildConfig
import com.ballchalu.application.App
import com.ballchalu.mqtt.MqttCallbackHandler
import com.ballchalu.mqtt.MqttConnection
import com.ballchalu.mqtt.MqttTraceCallback

import com.ballchalu.shared.database.prefs.PreferenceStorage
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Singleton

/**
 * Defines all the classes that need to be provided in the scope of the app.
 *
 * Define here all objects that are shared throughout the app, like SharedPreferences, navigators or
 * others. If some of those objects are singletons, they should be annotated with `@Singleton`.
 */
@Module
class AppModule {

    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesPreferenceStorage(context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)

    @Provides
    fun providesClipboardManager(context: Context): ClipboardManager =
        context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager

    @Singleton
    @Provides
    fun providesMqttConnectOptions(): MqttConnectOptions =
        MqttConnectOptions().also {
            it.isAutomaticReconnect = true
            it.isCleanSession = false
            it.keepAliveInterval = 5
            it.userName = BuildConfig.mqttUserName
            it.password = BuildConfig.mqttPassword.toCharArray()
        }

    @Singleton
    @Provides
    fun provideMqttClient(context: Context): MqttAndroidClient {
        val clientId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID);
        val port = 1883
        val uri: String
        uri = "${BuildConfig.mqttUrl}:$port"
        val client = MqttAndroidClient(context, uri, clientId)
        val clientHandle = uri + clientId
        val actionArgs = arrayOfNulls<String>(1)
        actionArgs[0] = clientId

        client.setCallback(MqttCallbackHandler(context, clientHandle))
        client.setTraceCallback(MqttTraceCallback())
        return client
    }

    @Singleton
    @Provides
    fun providesMqttConnection(
        context: Context,
        mqttAndroidClient: MqttAndroidClient,
        mqttConnectOptions: MqttConnectOptions
    ): MqttConnection =
        MqttConnection(context, mqttAndroidClient, mqttConnectOptions)

}

