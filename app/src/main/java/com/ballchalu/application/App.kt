package com.ballchalu.application

import android.provider.Settings
import com.ballchalu.R
import com.ballchalu.base.di.DaggerAppComponent
import com.ballchalu.mqtt.MqttConnection
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


class App : DaggerApplication() {

    @JvmField
    public var androidId: String = ""

    override fun onCreate() {
        super.onCreate()
        instance = this
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Play-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
        androidId = Settings.Secure.ANDROID_ID
        MqttConnection.connectToClient()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    companion object {
        lateinit var instance: App
    }
}